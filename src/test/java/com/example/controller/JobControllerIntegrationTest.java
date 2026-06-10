package com.example.controller;

import com.example.TestFixtures;
import com.example.exception.JobNotFoundException;
import com.example.service.JobService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.json.JsonMapper;

import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class JobControllerIntegrationTest extends TestFixtures {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonMapper jsonMapper;

    @MockitoBean
    private JobService jobService;

    @Test
    @DisplayName("GET /jobs/{id} should return 200 OK and JobResponse when valid ID is provided")
    void getJob_shouldReturnStatusCode200_andJobResponse() throws Exception {
        when(jobService.getJob(1L)).thenReturn(JOB_RESPONSE);

        mockMvc.perform(get(BASE_PATH + "/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("GET /jobs/{id} should return 400 BAD REQUEST when ID is less than 1")
    void getJob_shouldReturnStatusCode400_whenIdIsLessThan1() throws Exception {
        mockMvc.perform(get(BASE_PATH + "/0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(jobService);
    }

    @Test
    @DisplayName("GET /jobs/{id} should return 404 NOT FOUND when job with given ID does not exist")
    void getJob_shouldReturnStatusCode404_whenJobDoesNotExist() throws Exception {
        when(jobService.getJob(1L)).thenThrow(new JobNotFoundException(1L));

        mockMvc.perform(get(BASE_PATH + "/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /jobs should return 200 OK and a list of JobResponse")
    void getJobs_shouldReturnStatusCode200_andListOfJobResponse() throws Exception {
        when(jobService.getAllJobs()).thenReturn(JOB_RESPONSE_LIST);

        mockMvc.perform(get(BASE_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("POST /jobs should return 201 CREATED and JobResponse when JobRequest matches upper boundaries for name and description")
    void postJob_shouldReturnStatusCode201_andJobResponse_whenJobRequestMatchesUpperBoundaries() throws Exception {
        when(jobService.addJob(JOB_REQUEST_UPPER_BOUNDARIES)).thenReturn(JOB_RESPONSE_UPPER_BOUNDARIES);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(JOB_REQUEST_UPPER_BOUNDARIES)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("POST /jobs should return 201 CREATED and JobResponse when JobRequest matches lower boundaries for name and description")
    void postJob_shouldReturnStatusCode201_andJobResponse_whenJobRequestMatchesLowerBoundaries() throws Exception {
        when(jobService.addJob(JOB_REQUEST_LOWER_BOUNDARIES)).thenReturn(JOB_RESPONSE_LOWER_BOUNDARIES);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(JOB_REQUEST_LOWER_BOUNDARIES)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidNameAndDescPayloads")
    @DisplayName("POST /jobs should return 400 BAD REQUEST when validation fails")
    void postJob_shouldReturnStatusCode400_whenValidationFails(String jsonPayload) throws Exception {
        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(jobService);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidEnumPayloads")
    @DisplayName("POST /jobs should return 400 BAD REQUEST when invalid enum passed")
    void postJob_shouldReturnStatusCode400_whenInvalidEnumPassed(String jsonPayload) throws Exception {
        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(jobService);
    }

    @Test
    @DisplayName("DELETE /jobs/{id} should return 204 NO CONTENT when a valid ID is provided")
    void deleteJob_shouldReturnStatusCode204() throws Exception {
        doNothing().when(jobService).deleteJob(1L);

        mockMvc.perform(delete(BASE_PATH + "/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /jobs/{id} should return 400 BAD REQUEST when ID is less than 1")
    void deleteJob_shouldReturnStatusCode400_whenIdIsLessThan1() throws Exception {
        mockMvc.perform(delete(BASE_PATH + "/0"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(jobService);
    }

    @Test
    @DisplayName("DELETE /jobs/{id} should return 404 NOT FOUND when job with given ID does not exist")
    void deleteJob_shouldReturnStatusCode404_whenJobDoesNotExist() throws Exception {
        doThrow(new JobNotFoundException(1L)).when(jobService).deleteJob(1L);

        mockMvc.perform(delete(BASE_PATH + "/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /jobs/{id} should return 200 OK and a JobResponse when a valid ID and JobRequest are provided")
    void updateJob_shouldReturnStatusCode200_andJobResponse() throws Exception {
        when(jobService.updateJob(1L, JOB_REQUEST)).thenReturn(JOB_RESPONSE);

        mockMvc.perform(put(BASE_PATH + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(JOB_REQUEST)))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(JOB_RESPONSE)));
    }

    @Test
    @DisplayName("PUT /jobs/{id} should return 400 BAD REQUEST when ID is less than 1")
    void updateJob_shouldReturnStatusCode400_whenIdIsLessThanOne() throws Exception {
        mockMvc.perform(put(BASE_PATH + "/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(JOB_REQUEST)))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(jobService);
    }

    @Test
    @DisplayName("PUT /jobs/{id} should return 404 NOT FOUND when job with given ID does not exist")
    void updateJob_shouldReturn404_whenJobDoesNotExist() throws Exception {
        doThrow(new JobNotFoundException(1L)).when(jobService).updateJob(1L, JOB_REQUEST);

        mockMvc.perform(put(BASE_PATH + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMapper.writeValueAsString(JOB_REQUEST)))
                .andExpect(status().isNotFound());
    }

    private static Stream<String> provideInvalidNameAndDescPayloads() {
        return Stream.of(
                TestFixtures.JOB_REQUEST_BLANK_JOB_NAME,
                TestFixtures.JOB_REQUEST_BLANK_JOB_DESC,
                TestFixtures.JOB_REQUEST_JOB_NAME_TOO_LARGE,
                TestFixtures.JOB_REQUEST_JOB_DESC_TOO_LARGE
        );
    }

    private static Stream<String> provideInvalidEnumPayloads() {
        return Stream.of(
                TestFixtures.JOB_REQUEST_INVALID_BAND_JSON,
                TestFixtures.JOB_REQUEST_INVALID_CAPABILITY_JSON,
                TestFixtures.JOB_REQUEST_NULL_BAND_JSON,
                TestFixtures.JOB_REQUEST_NULL_CAPABILITY_JSON
        );
    }
}
