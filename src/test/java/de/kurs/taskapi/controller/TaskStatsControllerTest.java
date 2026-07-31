package de.kurs.taskapi.controller;

import de.kurs.taskapi.model.Task;
import de.kurs.taskapi.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.kurs.taskapi.controller.ApiExceptionHandler;

@WebMvcTest(controllers = {TaskStatsController.class, ApiExceptionHandler.class})
public class TaskStatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    void shouldReturnTotalCount() throws Exception {
        List<Task> tasks = List.of(
            new Task(1L, "Erste Aufgabe", false),
            new Task(2L, "Zweite Aufgabe", false),
            new Task(3L, "Dritte Aufgabe", false)
        );

        when(taskService.getAll()).thenReturn(tasks);

        mockMvc.perform(get("/api/stats/total"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(3));
    }

    @Test
    void shouldReturnCompletedCount() throws Exception {
        List<Task> tasks = List.of(
            new Task(1L, "Erste Aufgabe", true),
            new Task(2L, "Zweite Aufgabe", false),
            new Task(3L, "Dritte Aufgabe", true)
        );

        when(taskService.getAll()).thenReturn(tasks);

        mockMvc.perform(get("/api/stats/completed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(2));
    }

    @Test
    void shouldReturnOpenCount() throws Exception {
        List<Task> tasks = List.of(
            new Task(1L, "Erste Aufgabe", true),
            new Task(2L, "Zweite Aufgabe", false),
            new Task(3L, "Dritte Aufgabe", false)
        );

        when(taskService.getAll()).thenReturn(tasks);

        mockMvc.perform(get("/api/stats/open"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.open").value(2));
    }

    @Test
    void shouldReturnZeroWhenNoTasksExist() throws Exception {
        when(taskService.getAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/stats/total"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(0));
    }

    /*@Test
    void shouldReturnInternalServerErrorWhenServiceFalls() throws Exception{
        when(taskService.getAll()).thenThrow(new RuntimeException("Datenbank kaputt"));

        mockMvc.perform(get("/api/stats/total"))
        .andExpect(status().isInternalServerError());
    }*/
}