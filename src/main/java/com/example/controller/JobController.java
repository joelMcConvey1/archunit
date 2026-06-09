package com.example.controller;

import com.example.model.JobErrorResponse;
import com.example.model.JobRequest;
import com.example.model.JobResponse;
import com.example.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "/jobs")
@Validated
@Tag(name = "Jobs", description = "Endpoints for managing jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete job by ID", description = "Remove job from database")
    @ApiResponse(
            responseCode = "204",
            description = "Job successfully removed from database",
            content = @Content
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid ID (must be >= 1)",
            content = @Content(schema = @Schema(implementation = JobErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Job not found",
            content = @Content(schema = @Schema(implementation = JobErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "An error has occurred",
            content = @Content(schema = @Schema(implementation = JobErrorResponse.class))
    )
    public ResponseEntity<Void> deleteJob(@PathVariable("id") @Min(1) Long id) {
        jobService.deleteJob(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get an existing job by ID", description = "Return a job")
    @ApiResponse(
            responseCode = "200",
            description = "Returned job",
            content = @Content(schema = @Schema(implementation = JobResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid ID (must be >= 1)",
            content = @Content(schema = @Schema(implementation = JobErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Job not found",
            content = @Content(schema = @Schema(implementation = JobErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema(implementation = JobErrorResponse.class))
    )
    public ResponseEntity<JobResponse> getJob(@PathVariable("id") @Min(1) Long id) {
        return ResponseEntity.ok(jobService.getJob(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a list of all jobs", description = "Return all jobs")
    @ApiResponse(
            responseCode = "200",
            description = "Returned jobs (If no jobs exist, an empty list is returned)",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = JobResponse.class)))
    )
    @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema(implementation = JobErrorResponse.class))
    )
    public ResponseEntity<List<JobResponse>> getJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a job", description = "Add job to database")
    @ApiResponse(
            responseCode = "201",
            description = "Job successfully added to database",
            content = @Content(schema = @Schema(implementation = JobResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid request payload",
            content = @Content(schema = @Schema(implementation = JobErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "An error has occurred",
            content = @Content(schema = @Schema(implementation = JobErrorResponse.class))
    )
    public ResponseEntity<JobResponse> postJob(@Valid @RequestBody JobRequest jobRequest) {
        return ResponseEntity.status(CREATED).body(jobService.addJob(jobRequest));
    }

    @PutMapping(path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Update a job by ID", description = "Update a job in database")
    @ApiResponse(
            responseCode = "200",
            description = "Job successfully updated",
            content = @Content(schema = @Schema(implementation = JobResponse.class))
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid request payload",
            content = @Content(schema = @Schema(implementation = JobErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Job not found",
            content = @Content(schema = @Schema(implementation = JobErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "500",
            description = "An error has occurred",
            content = @Content(schema = @Schema(implementation = JobErrorResponse.class))
    )
    public ResponseEntity<JobResponse> updateJob(@PathVariable("id") @Min(1) Long id,
                                                 @Valid @RequestBody JobRequest jobRequest) {
        return ResponseEntity.ok(jobService.updateJob(id, jobRequest));
    }
}
