package de.kurs.taskapi.controller;

import de.kurs.taskapi.model.Priority;
import de.kurs.taskapi.model.Task;
import de.kurs.taskapi.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Nimmt HTTP-Anfragen an und ruft die Service-Schicht auf. */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public List<Task> getAll(@RequestParam(required = false) Boolean completed) {
        if (completed == null) {
            return service.getAll();
        }
        return service.getByStatus(completed);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task create(@Valid @RequestBody CreateTaskRequest request) {
        return service.create(request.title(), request.priority());
    }

    @PatchMapping("/{id}/complete")
    public Task complete(@PathVariable long id) {
        return service.complete(id);
    }

    @PatchMapping("/{id}/title")
    public Task updateTitle(@PathVariable long id, @Valid @RequestBody UpdateTitleRequest request) {
        return service.updateTitle(id, request.title());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    public record CreateTaskRequest(
            @NotBlank(message = "Der Titel darf nicht leer sein.")
            @Size(max = 100, message = "Der Titel darf höchstens 100 Zeichen enthalten.")
            String title,
            Priority priority) {
    }

    public record UpdateTitleRequest(
            @NotBlank(message = "Der Titel darf nicht leer sein.")
            @Size(max = 100, message = "Der Titel darf höchstens 100 Zeichen enthalten.")
            String title) {
    }
}