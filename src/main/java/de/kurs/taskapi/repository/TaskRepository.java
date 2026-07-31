package de.kurs.taskapi.repository;

import de.kurs.taskapi.model.Task;
import org.springframework.stereotype.Repository; // Kennzeichnet eine Datenzugriffsklasse.

import java.util.ArrayList; // Veränderbare Liste im Arbeitsspeicher.
import java.util.List; // Allgemeiner Listentyp.
import java.util.Optional; // Wert kann vorhanden oder nicht vorhanden sein.
import java.util.concurrent.atomic.AtomicLong; // Thread-sicherer Zähler für IDs.

/** Speichert Aufgaben nur im Arbeitsspeicher; nach Neustart sind sie weg. */
@Repository
public class TaskRepository {
    private final List<Task> tasks = new ArrayList<>(); // private: nur in dieser Klasse; final: Referenz bleibt gleich.
    private final AtomicLong sequence = new AtomicLong(0); // Beginnt die ID-Zählung bei 0.

    /** synchronized erlaubt immer nur einem Thread gleichzeitig den Zugriff auf die Methode. */
    public synchronized List<Task> findAll() {
        return List.copyOf(tasks); // Gibt eine nicht veränderbare Kopie zurück.
    }

    public synchronized Optional<Task> findById(long id) {
        // stream durchsucht die Liste; filter behält passende Elemente; findFirst nimmt den ersten Treffer.
        return tasks.stream().filter(task -> task.id() == id).findFirst();
    }

    public synchronized Task save(String title) {
        long id = sequence.incrementAndGet(); // Erhöht den Zähler und liefert die neue ID.
        Task task = new Task(id, title, false); // new erzeugt ein neues Objekt.
        tasks.add(task); // Fügt die Aufgabe zur Liste hinzu.
        return task; // Gibt die gespeicherte Aufgabe zurück.
    }

    public synchronized Optional<Task> markCompleted(long id) {
        for (int index = 0; index < tasks.size(); index++) { // Durchläuft alle Listenpositionen.
            Task current = tasks.get(index); // Liest das Element an der Position index.
            if (current.id() == id) { // if führt den Block nur bei wahrer Bedingung aus.
                Task updated = new Task(current.id(), current.title(), true);
                tasks.set(index, updated); // Ersetzt die alte durch die neue Aufgabe.
                return Optional.of(updated); // Optional mit vorhandenem Wert.
            }
        }
        return Optional.empty(); // Kein Treffer gefunden.
    }

    // Angepasste Methode wie 'markCompleted()'
    public synchronized Optional<Task> editTask(String title, long id) {
        for (int index = 0; index < tasks.size(); index++) { // Durchläuft alle Listenpositionen.
            Task current = tasks.get(index); // Liest das Element an der Position index.
            if (current.id() == id) { // if führt den Block nur bei wahrer Bedingung aus.
                Task updated = new Task(current.id(), title, current.completed()); // Unterschied zu markCompleted(): title wird durch request übergeben, completed() wird übernommen
                tasks.set(index, updated); // Ersetzt die alte durch die neue Aufgabe.
                return Optional.of(updated); // Optional mit vorhandenem Wert.
            }
        }
        return Optional.empty(); // Kein Treffer gefunden.
    }
}
