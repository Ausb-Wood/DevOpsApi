package de.kurs.taskapi.controller;

import org.springframework.web.bind.annotation.GetMapping; // Verknüpft HTTP GET mit einer Methode.
import org.springframework.web.bind.annotation.RequestMapping; // Gemeinsamer URL-Anfang.
import org.springframework.web.bind.annotation.RestController; // Rückgaben werden als JSON gesendet.

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map; // Schlüssel-Wert-Sammlung.

/** Liefert den technischen Zustand der Anwendung. */
@RestController
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping // Reagiert auf GET /api/health.
    public Map<String, String> health() {
        return Map.of("status", "UP", "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))); // Spring wandelt die Map in JSON um.
    }
}
