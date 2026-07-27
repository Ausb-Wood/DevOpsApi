package de.kurs.taskapi.model;

import jakarta.validation.constraints.NotBlank; // Verbietet leere Texte.
import jakarta.validation.constraints.Size; // Begrenzt die Textlänge.

/** Eine Aufgabe. record erzeugt automatisch Konstruktor und Zugriffsmethoden. */
public record Task(
        Long id, // Eindeutige Nummer; Long kann auch null sein.
        @NotBlank(message = "Der Titel darf nicht leer sein.")
        @Size(max = 100, message = "Der Titel darf höchstens 100 Zeichen enthalten.")
        String title, // String ist Text.
        boolean completed) { // boolean ist true oder false.
}
