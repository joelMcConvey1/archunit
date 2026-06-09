package com.example.handler;

import com.example.TestFixtures;
import com.example.exception.JobNotFoundException;
import com.example.service.JobService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class GlobalExceptionHandlerTest extends TestFixtures {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JobService jobService;

    @Test
    @DisplayName("Handle JobNotFoundException should return 404 NOT FOUND with message")
    void testHandleJobNotFoundException() throws Exception {
        when(jobService.getJob(1L)).thenThrow(new JobNotFoundException(1L));

        mockMvc.perform(get(BASE_PATH + "/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Job not found"));
    }

    @Test
    @DisplayName("Handle ConstraintViolationException should return 400 BAD REQUEST with message")
    void testHandleConstraintViolation() throws Exception {
        mockMvc.perform(get(BASE_PATH + "/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

    @Test
    @DisplayName("Handle MethodArgumentNotValidException should return 400 BAD REQUEST with message")
    void testHandleValidationException() throws Exception {
        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

    @Test
    @DisplayName("Handle HttpMessageNotReadableException should return 400 BAD REQUEST with message")
    void testHandleHttpMessageNotReadable() throws Exception {
        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JOB_REQUEST_INVALID_CAPABILITY_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Malformed JSON or invalid enum value"));
    }

    @Test
    @DisplayName("Handle generic exceptions should return 500 INTERNAL SERVER ERROR with message")
    void testHandleGenericException() throws Exception {
        when(jobService.getAllJobs()).thenThrow(new RuntimeException());

        mockMvc.perform(get(BASE_PATH))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Internal server error"));
    }
}