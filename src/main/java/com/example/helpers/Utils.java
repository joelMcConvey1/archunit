package com.example.helpers;

import com.example.model.Job;
import com.example.model.JobRequest;
import lombok.experimental.UtilityClass;

import static java.util.Objects.isNull;

@UtilityClass
public class Utils {

    /**
     * Normalises the Strings of a JobRequest by trimming leading and trailing whitespace
     * and collapsing multiple consecutive whitespace characters into a single space.
     */
    public static JobRequest normaliseJobRequest(JobRequest request) {
        return new JobRequest(
                normaliseString(request.jobName()),
                normaliseString(request.jobDescription()),
                request.capability(),
                request.band());
    }

    /**
     * Builds a new Job entity from a JobRequest instance.
     */
    public static Job buildJobFromRequest(JobRequest jobRequest) {
        return Job.builder()
                .jobName(jobRequest.jobName())
                .jobDescription(jobRequest.jobDescription())
                .capability(jobRequest.capability())
                .band(jobRequest.band())
                .build();
    }

    /**
     * Applies values from a JobRequest to an existing Job entity.
     */
    public static void updateJobFromRequest(JobRequest jobRequest, Job job) {
        job.setJobName(jobRequest.jobName());
        job.setJobDescription(jobRequest.jobDescription());
        job.setCapability(jobRequest.capability());
        job.setBand(jobRequest.band());
    }

    /**
     * Normalises text by stripping edges, cleaning whitespace around line breaks,
     * collapsing spaces/tabs within lines, and preserving paragraph breaks.
     */
    private String normaliseString(String input) {
        if (isNull(input)) {
            return null;
        }

        return input.strip()
                .replaceAll("\\R[ \\t]+", "\n")
                .replaceAll("[ \\t]+\\R", "\n")
                .replaceAll("[ \\t]+", " ")
                .replaceAll("\\R{2,}", "\n\n");
    }
}
