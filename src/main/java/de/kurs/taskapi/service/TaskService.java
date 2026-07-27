package de.kurs.taskapi.service;

import de.kurs.taskapi.model.Task;
import de.kurs.taskapi.repository.TaskRepository;
import org.springframework.stereotype.Service; // Kennzeichnet fachliche Anwendungslogik.

import java.util.List;
import java.util.NoSuchElementException; // Fehler für eine nicht gefundene Aufgabe.

/** Verbindet Controller und Repository und enthält fachliche Regeln. */
@Service
public class TaskService {
    private final TaskRepository repository;

    /** Constructor Injection: Spring übergibt das benötigte Repository. */
    public TaskService(TaskRepository repository) {
        this.repository = repository; // this meint das Feld des aktuellen Objekts.
    }

    public List<Task> getAll() {
        return repository.findAll();
    }

    public Task create(String title) {
        return repository.save(title.trim()); // trim entfernt Leerzeichen am Anfang und Ende.
    }

    public Task complete(long id) {
        return repository.markCompleted(id)
                // orElseThrow wirft einen Fehler, wenn Optional leer ist.
                .orElseThrow(() -> new NoSuchElementException("Aufgabe nicht gefunden: " + id));
    }
}
