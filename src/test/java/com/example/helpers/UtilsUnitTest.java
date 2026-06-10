package com.example.helpers;

import com.example.TestFixtures;
import com.example.model.Band;
import com.example.model.Capability;
import com.example.model.JobRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.helpers.Utils.normaliseJobRequest;
import static org.junit.jupiter.api.Assertions.*;

class UtilsUnitTest extends TestFixtures {

    @Test
    @DisplayName("normaliseJobRequest should return nulls for jobName and jobDescription when input fields are null")
    void normaliseJobRequest_shouldReturnNullsWhenInputFieldsNull() {
        JobRequest normalised = normaliseJobRequest(JOB_REQUEST_NULL_NAME_DESCRIPTION_JSON);

        assertNull(normalised.jobName());
        assertNull(normalised.jobDescription());
        assertEquals(Capability.DELIVERY, normalised.capability());
        assertEquals(Band.CONSULTANT, normalised.band());
    }

    @Test
    @DisplayName("normaliseJobRequest should trim and collapse whitespace in jobName and jobDescription")
    void normaliseJobRequest_shouldTrimAndCollapseWhitespace() {
        JobRequest normalised = normaliseJobRequest(JOB_REQUEST_STRING_SPACING_JSON);

        assertNotNull(normalised.jobName());
        assertFalse(normalised.jobName().startsWith(" "));
        assertFalse(normalised.jobName().endsWith(" "));
        assertFalse(normalised.jobName().contains("  "));

        assertNotNull(normalised.jobDescription());
        assertTrue(normalised.jobDescription().contains("\n\n"));
    }
}

