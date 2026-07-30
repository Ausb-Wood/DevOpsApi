package de.kurs.taskapi.controller;

import de.kurs.taskapi.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class TaskStatsController {

    private final TaskService taskService;

    public TaskStatsController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/total")
    public Map<String, Long> getTotalCount() {
        long count = taskService.getAll().size();
        return Map.of("total", count);
    }

    @GetMapping("/completed")
    public Map<String, Long> getCompletedCount() {
        long count = taskService.getAll().stream()
                .filter(task -> task.completed())
                .count();
        return Map.of("completed", count);
    }

    @GetMapping("/open")
    public Map<String, Long> getOpenCount() {
        long count = taskService.getAll().stream()
                .filter(task -> !task.completed())
                .count();
        return Map.of("open", count);
    }
}