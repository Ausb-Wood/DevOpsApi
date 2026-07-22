package de.kurs.taskapi; // package ordnet die Klasse einem Namensraum zu.

import org.springframework.boot.SpringApplication; // Startet die Spring-Anwendung.
import org.springframework.boot.autoconfigure.SpringBootApplication; // Aktiviert Spring Boot.

/** Startklasse der Anwendung. */
@SpringBootApplication // Sucht Komponenten und richtet Spring automatisch ein.
public class TaskApiApplication { // public: von überall sichtbar; class: Bauplan für Objekte.

    public static void main(String[] args) { // static: ohne Objekt aufrufbar; void: kein Rückgabewert.
        SpringApplication.run(TaskApiApplication.class, args); // Startet Webserver und Anwendung.
    }
}
