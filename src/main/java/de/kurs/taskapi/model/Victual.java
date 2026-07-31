package de.kurs.taskapi.model;

import de.kurs.taskapi.model.Diet;

import jakarta.validation.constraints.NotBlank; // Verbietet leere Texte.
import jakarta.validation.constraints.Size; // Begrenzt die Textlänge.

/** A Victual that contains a name, description to avoid ambiguity and a matching diet type */
public record Victual(
        Long id, // Eindeutige Nummer; Long kann auch null sein.
        @NotBlank(message = "Name shall not be empty")
        @Size(max = 100, message = "Name shall only have up to 100 characters")
        String name, // String ist Text.
        @Size(max = 255, message = "Description shall only have up to 255 characters")
        String description,
        Diet diet) { // boolean ist true oder false.
}
