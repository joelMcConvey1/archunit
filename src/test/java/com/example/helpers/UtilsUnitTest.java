package com.example.helpers;

import com.example.model.Band;
import com.example.model.Capability;
import com.example.model.JobRequest;
import org.junit.jupiter.api.Test;

import static com.example.helpers.Utils.normaliseJobRequest;
import static org.junit.jupiter.api.Assertions.*;

class UtilsUnitTest {

    @Test
    void normaliseJobRequest_shouldReturnNullsWhenInputFieldsNull() {
        JobRequest request = new JobRequest(null, null, Capability.DELIVERY, Band.CONSULTANT);

        JobRequest normalized = normaliseJobRequest(request);

        assertNull(normalized.jobName());
        assertNull(normalized.jobDescription());
        assertEquals(Capability.DELIVERY, normalized.capability());
        assertEquals(Band.CONSULTANT, normalized.band());
    }

    @Test
    void normaliseJobRequest_shouldTrimAndCollapseWhitespace() {
        String jobName = "  Senior   Software  \n\tEngineer\t  ";
        String jobDescription = "Build  \n  Production Grade\t\t\n\n  Systems  ";

        JobRequest request = new JobRequest(jobName, jobDescription, Capability.ENGINEERING, Band.SENIOR_ASSOCIATE);

        JobRequest normalized = normaliseJobRequest(request);

        assertNotNull(normalized.jobName());
        assertFalse(normalized.jobName().startsWith(" "));
        assertFalse(normalized.jobName().endsWith(" "));
        assertFalse(normalized.jobName().contains("  "));

        assertNotNull(normalized.jobDescription());
        assertTrue(normalized.jobDescription().contains("\n\n"));
    }
}

