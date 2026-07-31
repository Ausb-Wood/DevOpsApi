package de.kurs.taskapi.controller;

import de.kurs.taskapi.model.Task;
import de.kurs.taskapi.service.TaskService;
import jakarta.validation.Valid; // Startet die Prüfung der Eingabedaten.
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Nimmt HTTP-Anfragen an und ruft die Service-Schicht auf. */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService service;

    /** Spring übergibt den Service automatisch. */
    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping // GET /api/tasks
    public List<Task> getAll() {
        return service.getAll();
    }

    @PostMapping // POST /api/tasks
    @ResponseStatus(HttpStatus.CREATED) // Erfolgreiche Erstellung liefert HTTP 201.
    public Task create(@Valid @RequestBody CreateTaskRequest request) {
        // @RequestBody liest JSON; @Valid prüft die Regeln im record.
        return service.create(request.title());
    }

    @PatchMapping("/{id}/complete") // PATCH ändert nur einen Teil der Aufgabe.
    public Task complete(@PathVariable long id) {
        // @PathVariable liest die ID aus der URL.
        return service.complete(id);
    }

    /** DTO ist ein kleines Transportobjekt für eingehende JSON-Daten. */
    public record CreateTaskRequest(
            @NotBlank(message = "Der Titel darf nicht leer sein.")
            @Size(max = 100, message = "Der Titel darf höchstens 100 Zeichen enthalten.")
            String title) {
    }


    @PatchMapping("/{id}") // PATCH /api/tasks/{id} Änderung des Task-Titels
    public Task edit(@Valid @RequestBody CreateTaskRequest request, @PathVariable long id) {
        // @RequestBody liest JSON; @Valid prüft die Regeln im record.
        return service.edit(request.title(), id);
    }


    @GetMapping("/{id}") // GET /api/tasks/{id} Anzeigen des Tasks nach id
    public Task getTask(@PathVariable long id) {
        return service.getTask(id);
    }

}
