package de.kurs.taskapi.controller;

import org.junit.jupiter.api.BeforeEach; // Läuft vor jedem Test.
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc; // Simuliert HTTP ohne echten Netzwerkport.
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class GreetControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        GreetController controller = new GreetController();
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void returnsHalloWelt() throws Exception {
        mockMvc.perform(get("/api/greet"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hallo Welt"));
    }
}