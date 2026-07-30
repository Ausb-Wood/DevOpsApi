package de.kurs.taskapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper; // Spring Boot 3.5 verwendet Jackson 2 für JSON. // Wandelt Java-Objekte in JSON um.
import de.kurs.taskapi.repository.TaskRepository;
import de.kurs.taskapi.service.TaskService;
import org.junit.jupiter.api.BeforeEach; // Läuft vor jedem Test.
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc; // Simuliert HTTP ohne echten Netzwerkport.
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Prüft Statuscodes, JSON-Antworten und Eingabevalidierung. */
class TaskControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Für jeden Test wird eine frische, leere Anwendungskette aufgebaut.
        TaskService service = new TaskService(new TaskRepository());
        TaskController controller = new TaskController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ApiExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllInitiallyReturnsEmptyArray() throws Exception { // throws reicht unerwartete Fehler an JUnit weiter.
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk()) // HTTP 200 erwartet.
                .andExpect(jsonPath("$").isArray()) // $ bezeichnet die gesamte JSON-Antwort.
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void createReturnsCreatedTask() throws Exception {
        String body = objectMapper.writeValueAsString(
                new TaskController.CreateTaskRequest("Git-Branch anlegen")
        );

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON) // Der Request enthält JSON.
                        .content(body))
                .andExpect(status().isCreated()) // HTTP 201 erwartet.
                .andExpect(jsonPath("$.title").value("Git-Branch anlegen"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void createRejectsBlankTitle() throws Exception {
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"   \"}"))
                .andExpect(status().isBadRequest()) // HTTP 400 erwartet.
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void editReturnsCreatedTask() throws Exception {
        String body = objectMapper.writeValueAsString(
                new TaskController.CreateTaskRequest("Git-Branch anlegen")
        );

        mockMvc.perform(post("/api/tasks") // Task anlegen
                        .contentType(MediaType.APPLICATION_JSON) // Der Request enthält JSON.
                        .content(body))
                .andExpect(status().isCreated()); // HTTP 201 erwartet.
 
        mockMvc.perform(patch("/api/tasks/1") // Task bearbeiten
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Git-Branch angelegt\"}")) // ändert den Titel
                .andExpect(status().isOk()) // HTTP 200 erwartet.
                .andExpect(jsonPath("$.title").value("Git-Branch angelegt"))
                .andExpect(jsonPath("$.completed").value(false));
    }

    @Test
    void editRejectsBlankTitle() throws Exception {
                String body = objectMapper.writeValueAsString(
                new TaskController.CreateTaskRequest("Git-Branch anlegen")
        );

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON) // Der Request enthält JSON.
                        .content(body))
                .andExpect(status().isCreated()); // HTTP 201 erwartet.
        
        mockMvc.perform(patch("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"   \"}")) // ändert den Titel auf leeren String
                .andExpect(status().isBadRequest()) // HTTP 400 erwartet.
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void editRejectsExistingTitle() throws Exception {
                String body = objectMapper.writeValueAsString(
                new TaskController.CreateTaskRequest("Git-Branch anlegen")
        );
                String body2 = objectMapper.writeValueAsString(
                new TaskController.CreateTaskRequest("Git-Branch angelegt")
        );

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON) // Der Request enthält JSON.
                        .content(body))
                .andExpect(status().isCreated()); // HTTP 201 erwartet.

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON) // Der Request enthält JSON.
                        .content(body2))
                .andExpect(status().isCreated()); // HTTP 201 erwartet.

        mockMvc.perform(patch("/api/tasks/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)) // setzt den Titel des zweiten Tasks auf den des ersten
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void getTaskReturnsArrayWithTask() throws Exception { 
                String body = objectMapper.writeValueAsString(
                new TaskController.CreateTaskRequest("Git-Branch anlegen")
        );

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON) // Der Request enthält JSON.
                        .content(body))
                .andExpect(status().isCreated()); // HTTP 201 erwartet.
        
        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk()); // HTTP 200 erwartet.
    }
}
