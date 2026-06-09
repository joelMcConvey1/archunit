package com.example.service;

import com.example.TestFixtures;
import com.example.exception.JobNotFoundException;
import com.example.model.Job;
import com.example.model.JobRequest;
import com.example.model.JobResponse;
import com.example.persistence.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JobServiceUnitTest extends TestFixtures {

    @Mock
    private JobRepository jobRepository;

    @InjectMocks
    private JobService jobService;

    @Test
    @DisplayName("getJob should return a JobResponse when a job exists")
    void getJob_shouldReturnJobResponse_WhenJobExists() {
        when(jobRepository.findById(JOB_ENTITY.getId())).thenReturn(Optional.of(JOB_ENTITY));

        JobResponse response = jobService.getJob(JOB_ENTITY.getId());

        assertNotNull(response);

        assertAll(
                () -> assertEquals(JOB_ENTITY.getId(), response.id()),
                () -> assertEquals(JOB_ENTITY.getJobName(), response.jobName()),
                () -> assertEquals(JOB_ENTITY.getJobDescription(), response.jobDescription()),
                () -> assertEquals(JOB_ENTITY.getCapability(), response.capability()),
                () -> assertEquals(JOB_ENTITY.getBand(), response.band())
        );

        verify(jobRepository, times(1)).findById(JOB_ENTITY.getId());
    }

    @Test
    @DisplayName("getJob should throw JobNotFoundException when a job does not exist")
    void getJob_shouldThrowJobNotFoundException_WhenJobDoesNotExist() {
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(JobNotFoundException.class, () -> jobService.getJob(1L));

        verify(jobRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getAllJobs should return a list of JobResponse when jobs exist")
    void getAllJobs_shouldReturnListOfJobResponse_WhenJobsExist() {
        when(jobRepository.findAllByOrderByIdDesc()).thenReturn(JOB_ENTITY_LIST);

        List<JobResponse> jobResponses = jobService.getAllJobs();

        assertNotNull(jobResponses);

        assertAll(
                () -> assertEquals(JOB_ENTITY_LIST.getFirst().getId(), jobResponses.getFirst().id()),
                () -> assertEquals(JOB_ENTITY_LIST.getFirst().getJobName(), jobResponses.getFirst().jobName()),
                () -> assertEquals(JOB_ENTITY_LIST.getFirst().getJobDescription(), jobResponses.getFirst().jobDescription()),
                () -> assertEquals(JOB_ENTITY_LIST.getFirst().getCapability(), jobResponses.getFirst().capability()),
                () -> assertEquals(JOB_ENTITY_LIST.getFirst().getBand(), jobResponses.getFirst().band())
        );

        assertAll(
                () -> assertEquals(JOB_ENTITY_LIST.getLast().getId(), jobResponses.getLast().id()),
                () -> assertEquals(JOB_ENTITY_LIST.getLast().getJobName(), jobResponses.getLast().jobName()),
                () -> assertEquals(JOB_ENTITY_LIST.getLast().getJobDescription(), jobResponses.getLast().jobDescription()),
                () -> assertEquals(JOB_ENTITY_LIST.getLast().getCapability(), jobResponses.getLast().capability()),
                () -> assertEquals(JOB_ENTITY_LIST.getLast().getBand(), jobResponses.getLast().band())
        );

        verify(jobRepository, times(1)).findAllByOrderByIdDesc();
    }

    @Test
    @DisplayName("addJob should return a JobResponse when a valid JobRequest is saved")
    void addJob_shouldReturnJobResponse_whenValidJobRequestSaved() {
        when(jobRepository.save(any(Job.class))).thenReturn(JOB_ENTITY);

        JobResponse response = jobService.addJob(JOB_REQUEST);

        assertNotNull(response);

        assertAll(
                () -> assertEquals(JOB_REQUEST.jobName(), response.jobName()),
                () -> assertEquals(JOB_REQUEST.jobDescription(), response.jobDescription()),
                () -> assertEquals(JOB_REQUEST.capability(), response.capability()),
                () -> assertEquals(JOB_REQUEST.band(), response.band())
        );

        verify(jobRepository, times(1)).save(any(Job.class));
    }

    @Test
    @DisplayName("addJob should handle null jobName and jobDescription in JobRequest")
    void addJob_shouldHandleNullJobNameAndDescription() {
        JobRequest jobRequest = new JobRequest(null, null, null, null);

        Job nullJob = Job.builder()
                .id(1L)
                .jobName(null)
                .jobDescription(null)
                .capability(null)
                .band(null)
                .build();
        when(jobRepository.save(any(Job.class))).thenReturn(nullJob);

        JobResponse response = jobService.addJob(jobRequest);

        assertNotNull(response);
        assertNull(response.jobName());
        assertNull(response.jobDescription());
    }

    @Test
    @DisplayName("deleteJob should remove job from repository when job exists by id")
    void deleteJob_shouldRemoveJobFromRepository_whenJobExistsById() {
        when(jobRepository.existsById(1L)).thenReturn(true);

        jobService.deleteJob(1L);

        verify(jobRepository, times(1)).existsById(1L);
        verify(jobRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteJob should throw JobNotFoundException when job not found by id")
    void deleteJob_shouldThrowJobNotFoundException_whenJobNotFoundById() {
        when(jobRepository.existsById(1L)).thenReturn(false);

        assertThrows(JobNotFoundException.class, () -> jobService.deleteJob(1L));

        verify(jobRepository, times(1)).existsById(1L);
    }

    @Test
    @DisplayName("updateJob should update job and return JobResponse when job exists and new payload provided")
    void updateJob_shouldUpdateJob_AndReturnJobResponse_whenJobExists_andNewPayloadProvided() {
        Job job = Job.builder()
                .id(JOB_ENTITY.getId())
                .jobName(JOB_ENTITY.getJobName())
                .jobDescription(JOB_ENTITY.getJobDescription())
                .capability(JOB_ENTITY.getCapability())
                .band(JOB_ENTITY.getBand())
                .build();

        when(jobRepository.findById(1L)).thenReturn(Optional.of(job));

        when(jobRepository.save(any(Job.class))).thenReturn(job);

        JobResponse response = jobService.updateJob(job.getId(), JOB_REQUEST_UPPER_BOUNDARIES);

        assertNotNull(response);

        assertAll(
                () -> assertEquals(JOB_REQUEST_UPPER_BOUNDARIES.jobName(), response.jobName()),
                () -> assertEquals(JOB_REQUEST_UPPER_BOUNDARIES.jobDescription(), response.jobDescription()),
                () -> assertEquals(JOB_REQUEST_UPPER_BOUNDARIES.capability(), response.capability()),
                () -> assertEquals(JOB_REQUEST_UPPER_BOUNDARIES.band(), response.band())
        );

        verify(jobRepository, times(1)).findById(1L);
        verify(jobRepository, times(1)).save(any(Job.class));
    }

    @Test
    @DisplayName("updateJob should throw JobNotFoundException when job does not exist by id")
    void updateJob_shouldThrowJobNotFoundException_WhenJobDoesNotExistById() {
        when(jobRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(JobNotFoundException.class, () -> jobService.updateJob(1L, JOB_REQUEST));
    }
}
