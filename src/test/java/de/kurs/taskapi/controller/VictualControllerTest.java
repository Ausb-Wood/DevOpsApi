package de.kurs.taskapi.controller;

import de.kurs.taskapi.service.VictualService;
import de.kurs.taskapi.controller.VictualController;
import de.kurs.taskapi.repository.VictualRepository;
import de.kurs.taskapi.model.Diet;
import de.kurs.taskapi.model.Victual;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.*;

public class VictualControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private VictualService service;

    @BeforeEach
    void setup() {
        service = mock(VictualService.class);
        VictualController controller = new VictualController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(new ApiExceptionHandler())
            .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllInitiallyReturnsEmptyArray() throws Exception {
        mockMvc.perform(get("/api/victuals"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void createReturnsCreatedVictual() throws Exception {
        when(service.create(any(), any(), any())).thenReturn(new Victual(0L, "Bandnudeln", "Diese sind mit Ei", Diet.VEGETARIAN));

        String body = objectMapper.writeValueAsString(
            new VictualController.CreateVictualRequest("Bandnudeln", "Diese sind mit Ei", Diet.VEGETARIAN)
        );

        mockMvc.perform(post("/api/victuals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Bandnudeln"))
            .andExpect(jsonPath("$.description").value("Diese sind mit Ei"))
            .andExpect(jsonPath("$.diet").value(Diet.VEGETARIAN.name()));
    }

    @Test
    void updateReturnsUpdatedVictual() throws Exception {
        when(service.update(any(Long.class), any(), any(), any())).thenReturn(new Victual(0L, "Bandnudeln", "Diese sind ohne Ei", Diet.VEGAN));

        String body = objectMapper.writeValueAsString(
            new VictualController.UpdateVictualRequest("Bandnudeln", "Diese sind ohne Ei", Diet.VEGAN)
        );

        mockMvc.perform(patch("/api/victuals/0")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Bandnudeln"))
            .andExpect(jsonPath("$.description").value("Diese sind ohne Ei"))
            .andExpect(jsonPath("$.diet").value(Diet.VEGAN.name()));
    }
}
