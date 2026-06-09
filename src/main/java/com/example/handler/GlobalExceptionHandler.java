package com.example.handler;

import com.example.config.TimezoneConfiguration;
import com.example.exception.JobNotFoundException;
import com.example.model.JobErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private final DateTimeFormatter dateTimeFormatter;

    public GlobalExceptionHandler(TimezoneConfiguration timezoneConfiguration) {
        this.dateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(timezoneConfiguration.location());
    }

    @ExceptionHandler(JobNotFoundException.class)
    public ResponseEntity<JobErrorResponse> handleJobNotFound(JobNotFoundException ex) {
        log.warn(ex.getMessage());

        return buildResponseEntity(NOT_FOUND, "Job not found", List.of(ex.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<JobErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .toList();

        log.warn("Validation failed: {}", errors);

        return buildResponseEntity(BAD_REQUEST, "Validation failed", errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<JobErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        errors.addAll(ex.getBindingResult()
                .getGlobalErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .filter(Objects::nonNull)
                .toList());

        log.warn("Validation failed: {}", errors);

        return buildResponseEntity(BAD_REQUEST, "Validation failed", errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<JobErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.warn("Malformed JSON or invalid enum value: {}", ex.getMessage());

        return buildResponseEntity(BAD_REQUEST,
                "Malformed JSON or invalid enum value", List.of(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JobErrorResponse> handleGenericException(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);

        return buildResponseEntity(INTERNAL_SERVER_ERROR,
                "Internal server error", List.of("An unexpected error occurred"));
    }

    private ResponseEntity<JobErrorResponse> buildResponseEntity(HttpStatus status, String message, List<String> details) {
        JobErrorResponse response = createErrorResponse(status, message, details);

        return ResponseEntity.status(status).body(response);
    }

    private JobErrorResponse createErrorResponse(HttpStatus status, String message, List<String> details) {
        return new JobErrorResponse(
                status.value(),
                message,
                details,
                dateTimeFormatter.format(Instant.now())
        );
    }
}
