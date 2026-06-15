package com.example.model;

public record JobResponse(
        Long id,
        String jobName,
        String jobDescription,
        Capability capability,
        Band band
) {
    public static JobResponse fromJob(Job job) {
        return new JobResponse(
                job.getId(),
                job.getJobName(),
                job.getJobDescription(),
                job.getCapability(),
                job.getBand()
        );
    }
}
