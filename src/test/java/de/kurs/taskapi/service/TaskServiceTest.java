package de.kurs.taskapi.service;

import de.kurs.taskapi.model.Task;
import de.kurs.taskapi.repository.TaskRepository;
import org.junit.jupiter.api.Test; // Kennzeichnet eine automatisch ausführbare Testmethode.

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*; // Stellt Prüfmethoden wie assertEquals bereit.

/** Unit-Tests prüfen eine kleine Codeeinheit ohne Webserver. */
class TaskServiceTest {
    private final TaskService service = new TaskService(new TaskRepository());

    @Test
    void createTrimsTitleAndCreatesOpenTask() {
        Task task = service.create("  CI-Pipeline erstellen  "); // Führt die zu testende Aktion aus.

        assertEquals("CI-Pipeline erstellen", task.title()); // Erwarteter und tatsächlicher Wert müssen gleich sein.
        assertFalse(task.completed()); // Erwartet false.
        assertNotNull(task.id()); // Erwartet einen vorhandenen Wert.
    }

    @Test
    void completeMarksExistingTaskAsCompleted() {
        Task created = service.create("Dockerfile erstellen");

        Task completed = service.complete(created.id());

        assertTrue(completed.completed()); // Erwartet true.
        assertEquals(created.id(), completed.id());
    }

    @Test
    void completeRejectsUnknownId() {
        // assertThrows erwartet, dass der folgende Aufruf genau diesen Fehler auslöst.
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> service.complete(999) // Lambda: kleine Funktion ohne eigenen Methodennamen.
        );

        assertTrue(exception.getMessage().contains("999"));
    }

    @Test
    void editTrimsTitleAndEditsOpenTask() {
        Task task = service.create("  CI-Pipeline erstellen  "); // Führt die Erstellung des Tasks aus.
        
        Task editedTask = service.edit("    CI-Pipeline gepipet    ", task.id());  // Bearbeitet erstellten Task

        assertEquals("CI-Pipeline gepipet", editedTask.title()); // Erwarteter und tatsächlicher Wert müssen gleich sein.
        assertFalse(editedTask.completed()); // Erwartet false.
        assertNotNull(editedTask.id()); // Erwartet einen vorhandenen Wert.
    }

    @Test
    void getTaskRejectsUnknownId() {
        // assertThrows erwartet, dass der folgende Aufruf genau diesen Fehler auslöst.
        NoSuchElementException exception = assertThrows(
                NoSuchElementException.class,
                () -> service.complete(999) // Lambda: kleine Funktion ohne eigenen Methodennamen.
        );

        assertTrue(exception.getMessage().contains("999"));
    }
}
