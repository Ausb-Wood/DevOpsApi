package de.kurs.taskapi.controller;

import org.springframework.web.bind.annotation.GetMapping; // Verknüpft HTTP GET mit einer Methode.
import org.springframework.web.bind.annotation.RequestMapping; // Gemeinsamer URL-Anfang.
import org.springframework.web.bind.annotation.RestController; // Rückgaben werden als JSON gesendet.

@RestController
@RequestMapping("/api/greet")

public class GreetController {
    @GetMapping
    public String greet() {
        return "Hallo Welt";
    }
}
