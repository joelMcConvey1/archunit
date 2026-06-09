package com.example.service;

import com.example.exception.JobNotFoundException;
import com.example.model.Job;
import com.example.model.JobRequest;
import com.example.model.JobResponse;
import com.example.persistence.JobRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.helpers.Utils.*;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public JobResponse getJob(Long id) {
        return JobResponse.fromJob(jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException(id)));
    }

    public List<JobResponse> getAllJobs() {
        return jobRepository.findAllByOrderByIdDesc().stream().map(JobResponse::fromJob).toList();
    }

    public JobResponse addJob(JobRequest jobRequest) {
        JobRequest normalisedJobRequest = normaliseJobRequest(jobRequest);

        Job jobEntity = buildJobFromRequest(normalisedJobRequest);

        return JobResponse.fromJob(jobRepository.save(jobEntity));
    }

    @Transactional
    public void deleteJob(Long id) {
        if (!jobRepository.existsById(id)) {
            throw new JobNotFoundException(id);
        }

        jobRepository.deleteById(id);
    }

    @Transactional
    public JobResponse updateJob(Long id, JobRequest jobRequest) {
        Job jobEntity = jobRepository.findById(id).orElseThrow(() -> new JobNotFoundException(id));

        JobRequest normalisedJobRequest = normaliseJobRequest(jobRequest);
        
        updateJobFromRequest(normalisedJobRequest, jobEntity);

        return JobResponse.fromJob(jobRepository.save(jobEntity));
    }
}
