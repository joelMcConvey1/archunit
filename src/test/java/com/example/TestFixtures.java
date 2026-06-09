package com.example;

import com.example.model.Job;
import com.example.model.JobRequest;
import com.example.model.JobResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.aspectj.util.FileUtil;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.util.List;

public abstract class TestFixtures {
    private static final ObjectMapper objectMapper =
            new ObjectMapper().registerModule(new JavaTimeModule());

    protected static String BASE_PATH;
    protected static String ACTUATOR_HEALTH_ENDPOINT;

    protected static JobRequest JOB_REQUEST;
    protected static JobResponse JOB_RESPONSE;

    protected static JobRequest JOB_REQUEST_UPDATE;

    protected static Job JOB_ENTITY;
    protected static List<Job> JOB_ENTITY_LIST;

    protected static List<JobRequest> JOB_REQUEST_LIST;
    protected static List<JobResponse> JOB_RESPONSE_LIST;

    protected static JobRequest JOB_REQUEST_UPPER_BOUNDARIES;
    protected static JobResponse JOB_RESPONSE_UPPER_BOUNDARIES;

    protected static JobRequest JOB_REQUEST_LOWER_BOUNDARIES;
    protected static JobResponse JOB_RESPONSE_LOWER_BOUNDARIES;

    protected static String JOB_REQUEST_BLANK_JOB_NAME;
    protected static String JOB_REQUEST_BLANK_JOB_DESC;

    protected static String JOB_REQUEST_JOB_NAME_TOO_LARGE;
    protected static String JOB_REQUEST_JOB_DESC_TOO_LARGE;

    protected static String JOB_REQUEST_INVALID_BAND_JSON;
    protected static String JOB_REQUEST_INVALID_CAPABILITY_JSON;
    protected static String JOB_REQUEST_NULL_BAND_JSON;
    protected static String JOB_REQUEST_NULL_CAPABILITY_JSON;

    @BeforeAll
    public static void setupBeforeClass() throws Exception {
        BASE_PATH = "/jobs";
        ACTUATOR_HEALTH_ENDPOINT = "/actuator/health";

        JOB_REQUEST = objectMapper.readValue(readJsonFile("JOB_REQUEST.json"), JobRequest.class);
        JOB_RESPONSE = objectMapper.readValue(readJsonFile("JOB_RESPONSE.json"), JobResponse.class);

        JOB_REQUEST_UPDATE = objectMapper.readValue(readJsonFile("JOB_REQUEST_UPDATE.json"), JobRequest.class);

        JOB_ENTITY = objectMapper.readValue(readJsonFile("JOB_ENTITY.json"), Job.class);

        CollectionType entityList = objectMapper.getTypeFactory().constructCollectionType(List.class, Job.class);
        JOB_ENTITY_LIST = objectMapper.readValue(readJsonFile("JOB_ENTITY_LIST.json"), entityList);

        CollectionType requestList = objectMapper.getTypeFactory().constructCollectionType(List.class, JobRequest.class);
        JOB_REQUEST_LIST = objectMapper.readValue(readJsonFile("JOB_REQUEST_LIST.json"), requestList);

        CollectionType responseList = objectMapper.getTypeFactory().constructCollectionType(List.class, JobResponse.class);
        JOB_RESPONSE_LIST = objectMapper.readValue(readJsonFile("JOB_RESPONSE_LIST.json"), responseList);

        JOB_REQUEST_UPPER_BOUNDARIES = objectMapper.readValue(readJsonFile("JOB_REQUEST_UPPER_BOUNDARIES.json"), JobRequest.class);
        JOB_RESPONSE_UPPER_BOUNDARIES = objectMapper.readValue(readJsonFile("JOB_RESPONSE_UPPER_BOUNDARIES.json"), JobResponse.class);

        JOB_REQUEST_LOWER_BOUNDARIES = objectMapper.readValue(readJsonFile("JOB_REQUEST_LOWER_BOUNDARIES.json"), JobRequest.class);
        JOB_RESPONSE_LOWER_BOUNDARIES = objectMapper.readValue(readJsonFile("JOB_RESPONSE_LOWER_BOUNDARIES.json"), JobResponse.class);

        JOB_REQUEST_BLANK_JOB_NAME = readJsonFile("JOB_REQUEST_BLANK_NAME.json");
        JOB_REQUEST_BLANK_JOB_DESC = readJsonFile("JOB_REQUEST_BLANK_DESC.json");

        JOB_REQUEST_JOB_NAME_TOO_LARGE = readJsonFile("JOB_REQUEST_NAME_TOO_LARGE.json");
        JOB_REQUEST_JOB_DESC_TOO_LARGE = readJsonFile("JOB_REQUEST_DESC_TOO_LARGE.json");

        JOB_REQUEST_INVALID_BAND_JSON = readJsonFile("JOB_REQUEST_INVALID_BAND.json");
        JOB_REQUEST_INVALID_CAPABILITY_JSON = readJsonFile("JOB_REQUEST_INVALID_CAPABILITY.json");
        JOB_REQUEST_NULL_BAND_JSON = readJsonFile("JOB_REQUEST_NULL_BAND.json");
        JOB_REQUEST_NULL_CAPABILITY_JSON = readJsonFile("JOB_REQUEST_NULL_CAPABILITY.json");
    }

    private static String readJsonFile(String fileName) throws Exception {
        return FileUtil.readAsString(new File("src/test/resources/payloads/" + fileName));
    }
}