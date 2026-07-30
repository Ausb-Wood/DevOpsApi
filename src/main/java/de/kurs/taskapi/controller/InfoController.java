package de.kurs.taskapi.controller;

import de.kurs.taskapi.model.InfoResponse;
import de.kurs.taskapi.service.InfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller responsible for handling HTTP requests related to application information.
 * 
 * Controller responsibilities:
 * Receive the HTTP request.
 * Call the appropriate service.
 * Return the response.
 * 
 * Spring automatically detects this class because of the @RestController annotation.
 */
@RestController
@RequestMapping("/api/info")

public class InfoController {
    
    private final InfoService service;

    public InfoController(InfoService service) {
        this.service = service;
    }

    @GetMapping
    public InfoResponse getInfo() {
        return service.getApplicationInfo();
    }
}
