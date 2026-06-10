package com.example.service;

import com.example.TestFixtures;
import com.example.exception.JobNotFoundException;
import com.example.model.Band;
import com.example.model.Capability;
import com.example.model.Job;
import com.example.model.JobResponse;
import com.example.persistence.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(JobService.class)
class JobServiceIntegrationTest extends TestFixtures {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobService jobService;

    @Test
    @DisplayName("getJob should return JobResponse when valid job ID is provided")
    void getJob_shouldReturnJob() {
        jobService.addJob(JOB_REQUEST);

        Job savedJob = jobRepository.findAll().stream().findFirst().orElseThrow();

        JobResponse jobResponse = jobService.getJob(savedJob.getId());

        assertNotNull(jobResponse);

        assertAll(
                () -> assertEquals(JOB_REQUEST.jobName(), jobResponse.jobName()),
                () -> assertEquals(JOB_REQUEST.jobDescription(), jobResponse.jobDescription()),
                () -> assertEquals(JOB_REQUEST.capability(), jobResponse.capability()),
                () -> assertEquals(JOB_REQUEST.band(), jobResponse.band())
        );
    }

    @Test
    @DisplayName("getJob should throw JobNotFoundException when no job found for given ID")
    void getJob_shouldThrowJobNotFoundException_WhenNoJobFound() {
        assertThrows(JobNotFoundException.class, () -> jobService.getJob(1L));
    }

    @Test
    @DisplayName("getAllJobs should return list of JobResponses (Order By Id) when jobs exist in repository")
    void getAllJobs_shouldReturnListOf_JobResponses() {
        jobService.addJob(JOB_REQUEST_LIST.getFirst());
        jobService.addJob(JOB_REQUEST_LIST.getLast());

        List<JobResponse> jobResponses = jobService.getAllJobs();

        assertNotNull(jobResponses);

        assertAll(
                () -> assertEquals(JOB_REQUEST_LIST.getFirst().jobName(), jobResponses.getLast().jobName()),
                () -> assertEquals(JOB_REQUEST_LIST.getFirst().jobDescription(), jobResponses.getLast().jobDescription()),
                () -> assertEquals(JOB_REQUEST_LIST.getFirst().capability(), jobResponses.getLast().capability()),
                () -> assertEquals(JOB_REQUEST_LIST.getFirst().band(), jobResponses.getLast().band())
        );

        assertAll(
                () -> assertEquals(JOB_REQUEST_LIST.getLast().jobName(), jobResponses.getFirst().jobName()),
                () -> assertEquals(JOB_REQUEST_LIST.getLast().jobDescription(), jobResponses.getFirst().jobDescription()),
                () -> assertEquals(JOB_REQUEST_LIST.getLast().capability(), jobResponses.getFirst().capability()),
                () -> assertEquals(JOB_REQUEST_LIST.getLast().band(), jobResponses.getFirst().band())
        );
    }

    @Test
    @DisplayName("getAllJobs should return empty list when no jobs exist in repository")
    void getAllJobs_shouldReturnEmptyList_whenNoJobsExist() {
        List<JobResponse> jobResponses = jobService.getAllJobs();

        assertEquals(List.of(), jobResponses);
        assertTrue(jobResponses.isEmpty());
    }

    @Test
    @DisplayName("addJob should save job and return JobResponse")
    void addJob_shouldSaveAndReturnJobResponse() {
        JobResponse jobResponse = jobService.addJob(JOB_REQUEST);

        assertNotNull(jobResponse);

        assertAll(
                () -> assertEquals(JOB_REQUEST.jobName(), jobResponse.jobName()),
                () -> assertEquals(JOB_REQUEST.jobDescription(), jobResponse.jobDescription()),
                () -> assertEquals(JOB_REQUEST.capability(), jobResponse.capability()),
                () -> assertEquals(JOB_REQUEST.band(), jobResponse.band())
        );
    }

    @Test
    @DisplayName("addJob should save job to repository and return JobResponse when name and description reach upper boundary")
    void addJob_shouldSaveAndReturnJobResponse_whenNameAndDescriptionReachUpperBoundary() {
        JobResponse jobResponse = jobService.addJob(JOB_REQUEST_UPPER_BOUNDARIES);

        assertNotNull(jobResponse);

        assertAll(
                () -> assertEquals(JOB_REQUEST_UPPER_BOUNDARIES.jobName(), jobResponse.jobName()),
                () -> assertEquals(JOB_REQUEST_UPPER_BOUNDARIES.jobDescription(), jobResponse.jobDescription()),
                () -> assertEquals(JOB_REQUEST_UPPER_BOUNDARIES.capability(), jobResponse.capability()),
                () -> assertEquals(JOB_REQUEST_UPPER_BOUNDARIES.band(), jobResponse.band())
        );
    }

    @Test
    @DisplayName("addJob should save job to repository and return JobResponse when name and description reach lower boundary")
    void addJob_shouldSaveAndReturnJobResponse_whenNameAndDescriptionReachLowerBoundary() {
        JobResponse jobResponse = jobService.addJob(JOB_REQUEST_LOWER_BOUNDARIES);

        assertNotNull(jobResponse);

        assertAll(
                () -> assertEquals(JOB_REQUEST_LOWER_BOUNDARIES.jobName(), jobResponse.jobName()),
                () -> assertEquals(JOB_REQUEST_LOWER_BOUNDARIES.jobDescription(), jobResponse.jobDescription()),
                () -> assertEquals(JOB_REQUEST_LOWER_BOUNDARIES.capability(), jobResponse.capability()),
                () -> assertEquals(JOB_REQUEST_LOWER_BOUNDARIES.band(), jobResponse.band())
        );
    }

    @Test
    @DisplayName("deleteJob should remove job from repository when valid job ID is provided")
    void deleteJob_shouldRemoveJobFromRepository_whenValidJobIdProvided() {
        Job job = Job.builder()
                .jobName("Software Engineer")
                .jobDescription("Design, develop, test, and maintain software systems to solve user problems.")
                .capability(Capability.ENGINEERING)
                .band(Band.ASSOCIATE)
                .build();
        jobRepository.save(job);

        jobService.deleteJob(job.getId());

        assertTrue(jobRepository.findById(job.getId()).isEmpty());
    }

    @Test
    @DisplayName("deleteJob should throw JobNotFoundException when no job found for given ID")
    void deleteJob_shouldThrowJobNotFoundException_WhenNoJobFound() {
        assertThrows(JobNotFoundException.class, () -> jobService.deleteJob(1L));
    }

    @Test
    @DisplayName("updateJob should update job and return updated JobResponse when valid job ID is provided with existing data")
    void updateJob_shouldUpdateJob_andReturnUpdatedJob() {
        Job job = Job.builder()
                .jobName("Product Consultant")
                .jobDescription("Create a clear product vision and roadmap.")
                .capability(Capability.PRODUCT)
                .band(Band.SENIOR_ASSOCIATE)
                .build();
        jobRepository.save(job);

        JobResponse jobResponse = jobService.updateJob(job.getId(), JOB_REQUEST);

        assertAll(
                () -> assertEquals(JOB_REQUEST.jobName(), jobResponse.jobName()),
                () -> assertEquals(JOB_REQUEST.jobDescription(), jobResponse.jobDescription()),
                () -> assertEquals(JOB_REQUEST.capability(), jobResponse.capability()),
                () -> assertEquals(JOB_REQUEST.band(), jobResponse.band())
        );
    }

    @Test
    @DisplayName("updateJob should update job and return updated JobResponse when valid job ID is provided and name and description reach upper boundary")
    void updateJob_shouldUpdateJob_andReturnUpdatedJob_whenNameAndDescReachUpperBoundary() {
        Job job = Job.builder()
                .jobName("Product Consultant")
                .jobDescription("Provides guidance to clients on their product strategy, development, and management.")
                .capability(Capability.PRODUCT)
                .band(Band.SENIOR_ASSOCIATE)
                .build();
        jobRepository.save(job);

        JobResponse jobResponse = jobService.updateJob(job.getId(), JOB_REQUEST_UPPER_BOUNDARIES);

        assertAll(
                () -> assertEquals(JOB_REQUEST_UPPER_BOUNDARIES.jobName(), jobResponse.jobName()),
                () -> assertEquals(JOB_REQUEST_UPPER_BOUNDARIES.jobDescription(), jobResponse.jobDescription()),
                () -> assertEquals(JOB_REQUEST_UPPER_BOUNDARIES.capability(), jobResponse.capability()),
                () -> assertEquals(JOB_REQUEST_UPPER_BOUNDARIES.band(), jobResponse.band())
        );
    }

    @Test
    @DisplayName("updateJob should update job and return updated JobResponse when valid job ID is provided and name and description reach lower boundary")
    void updateJob_shouldUpdateJob_andReturnUpdatedJob_whenNameAndDescReachLowerBoundary() {
        Job job = Job.builder()
                .jobName("Product Consultant")
                .jobDescription("Provides guidance to clients on their product strategy, development, and management.")
                .capability(Capability.PRODUCT)
                .band(Band.SENIOR_ASSOCIATE)
                .build();
        jobRepository.save(job);

        JobResponse jobResponse = jobService.updateJob(job.getId(), JOB_REQUEST_LOWER_BOUNDARIES);

        assertAll(
                () -> assertEquals(JOB_REQUEST_LOWER_BOUNDARIES.jobName(), jobResponse.jobName()),
                () -> assertEquals(JOB_REQUEST_LOWER_BOUNDARIES.jobDescription(), jobResponse.jobDescription()),
                () -> assertEquals(JOB_REQUEST_LOWER_BOUNDARIES.capability(), jobResponse.capability()),
                () -> assertEquals(JOB_REQUEST_LOWER_BOUNDARIES.band(), jobResponse.band())
        );
    }

    @Test
    @DisplayName("updateJob should update field lastUpdated in database")
    void updateJob_shouldUpdateFieldLastUpdatedInDatabase() {
        Job job = Job.builder()
                .jobName("Product Consultant")
                .jobDescription("Provides guidance to clients on their product strategy, development, and management.")
                .capability(Capability.PRODUCT)
                .band(Band.SENIOR_ASSOCIATE)
                .build();
        job = jobRepository.save(job);

        Instant createdAt = job.getCreatedAt();

        jobService.updateJob(job.getId(), JOB_REQUEST);

        Job updatedJob = jobRepository.findById(job.getId()).orElseThrow();

        assertNotNull(updatedJob.getUpdatedAt());
        assertTrue(updatedJob.getUpdatedAt().isAfter(createdAt));
    }

    @Test
    @DisplayName("updateJob should throw JobNotFoundException when no job found for given ID")
    void updateJob_shouldThrowJobNotFoundException_WhenNoJobFound() {
        assertThrows(JobNotFoundException.class, () -> jobService.updateJob(1L, JOB_REQUEST));
    }
}
