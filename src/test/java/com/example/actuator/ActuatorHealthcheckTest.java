package com.example.actuator;

import com.example.TestFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ActuatorHealthcheckTest extends TestFixtures {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /actuator/health should return OK 200 and status UP")
    void actuatorHealthcheck_ShouldReturn200_andUp() throws Exception {
        mockMvc.perform(get(ACTUATOR_HEALTH_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }
}
