package com.example.controller;

import com.example.TestFixtures;
import com.example.model.JobRequest;
import com.example.model.JobResponse;
import com.example.service.JobService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobControllerUnitTest extends TestFixtures {

    @Mock
    private JobService jobService;

    @InjectMocks
    private JobController jobController;

    @Test
    @DisplayName("getJob should return 200 OK and JobResponse when job exists")
    void getJob_shouldReturn200_andJobResponse() {
        when(jobService.getJob(JOB_RESPONSE.id())).thenReturn(JOB_RESPONSE);

        ResponseEntity<JobResponse> response = jobController.getJob(1L);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );

        verify(jobService, times(1)).getJob(1L);
    }

    @Test
    @DisplayName("getJobs should return 200 OK and a list of JobResponse")
    void getJobs_shouldReturn200_andListOfJobResponse() {
        when(jobService.getAllJobs()).thenReturn(JOB_RESPONSE_LIST);

        ResponseEntity<List<JobResponse>> response = jobController.getJobs();

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );

        verify(jobService, times(1)).getAllJobs();
    }

    @Test
    @DisplayName("getJobs should return 200 OK and empty list when no jobs exist")
    void getJobs_shouldReturn200_andEmptyList_whenNoJobsExist() {
        when(jobService.getAllJobs()).thenReturn(List.of());

        ResponseEntity<List<JobResponse>> response = jobController.getJobs();

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );

        verify(jobService, times(1)).getAllJobs();
    }

    @Test
    @DisplayName("postJob should return 201 CREATED and JobResponse when job is added successfully")
    void postJob_shouldReturn201_andJobResponse() {
        when(jobService.addJob(JOB_REQUEST)).thenReturn(JOB_RESPONSE);

        ResponseEntity<JobResponse> response = jobController.postJob(JOB_REQUEST);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode())
        );

        verify(jobService, times(1)).addJob(any(JobRequest.class));
    }

    @Test
    @DisplayName("deleteJob should return 204 NO CONTENT when job is deleted")
    void deleteJob_shouldReturn204_whenJobIsDeleted() {
        doNothing().when(jobService).deleteJob(1L);

        ResponseEntity<Void> response = jobController.deleteJob(1L);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode())
        );

        verify(jobService, times(1)).deleteJob(1L);
    }

    @Test
    @DisplayName("updateJob should return 200 OK and JobResponse when job is updated")
    void updateJob_shouldReturn200_andJobResponse() {
        when(jobService.updateJob(1L, JOB_REQUEST)).thenReturn(JOB_RESPONSE);

        ResponseEntity<JobResponse> response = jobController.updateJob(JOB_RESPONSE.id(), JOB_REQUEST);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode())
        );

        verify(jobService, times(1)).updateJob(JOB_RESPONSE.id(), JOB_REQUEST);
    }
}
