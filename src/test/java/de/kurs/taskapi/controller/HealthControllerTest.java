package de.kurs.taskapi.controller;

import org.junit.jupiter.api.BeforeEach; // Läuft vor jedem Test.
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc; // Simuliert HTTP ohne echten Netzwerkport.
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class HealthControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        HealthController controller = new HealthController();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void returnsStatusAndDateTime() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}