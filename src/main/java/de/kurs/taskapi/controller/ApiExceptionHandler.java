package de.kurs.taskapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;

/** Wandelt Java-Fehler in verständliche HTTP- und JSON-Antworten um. */
@RestControllerAdvice // Gilt für alle REST-Controller.
public class ApiExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class) // Reagiert auf "nicht gefunden".
    @ResponseStatus(HttpStatus.NOT_FOUND) // HTTP 404.
    public Map<String, String> handleNotFound(NoSuchElementException exception) {
        return Map.of("error", exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // Reagiert auf ungültige Eingaben.
    @ResponseStatus(HttpStatus.BAD_REQUEST) // HTTP 400.
    public Map<String, String> handleValidation(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .findFirst() // Für die kurze Antwort wird nur der erste Fehler verwendet.
                .map(error -> error.getDefaultMessage() == null
                        ? "Ungültige Eingabe" // ?: ist eine kurze Wenn-dann-Auswahl.
                        : error.getDefaultMessage())
                .orElse("Ungültige Eingabe");
        return Map.of("error", message);
    }

    @ExceptionHandler(IllegalStateException.class) // Reagiert auf doppelte Eingaben.
    @ResponseStatus(HttpStatus.CONFLICT) // HTTP 409.
    public Map<String, String> handleIllegalState(IllegalStateException exception) {
    return Map.of("error", exception.getMessage());
}
}
