package de.kurs.taskapi.service;

import de.kurs.taskapi.repository.VictualRepository;
import de.kurs.taskapi.model.Victual;
import de.kurs.taskapi.model.Diet;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * 
 * VictualService
 */
@Service
public class VictualService {
    private final VictualRepository repository;

    public VictualService(VictualRepository repository) {
        this.repository = repository;
    }

    /**
     * Calls repository method to get all victuals
     * 
     * @return All victuals currently stored in the repository as List of victuals
     */
    public List<Victual> getAll() {
        return repository.findAll();
    }

    /**
     * Calls repository method with the given parameters to create a new victual
     * 
     * @param name The name of the victual
     * @param description The victuals description
     * @param diet The diet applicable for the victual
     * @return The created victual
     */
    public Victual create(String name, String description, Diet diet) {
        return repository.save(name.trim(), description, diet);
    }

    /**
     * Calls repository to find a target victual by its id or throws an exception
     * 
     * @param id The target victuals id
     * @return The found victual
     * @throws NoSuchElementException If the victual with the given id does not exist
     */
    public Victual findById(long id) {
        return repository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Viktualie nicht gefunden: " + id));
    }

    /**
     * Invokes repository method to update a target victual by its id or throws an exception if it does not exist
     * 
     * @param id The target victuals id
     * @param name The new victuals name or null
     * @param description The new victuals description or null
     * @param diet The new victuals diet or null
     * @return The updated victual
     * @throws NoSuchElementException If target victual with given id does not exist in the repository
     */
    public Victual update(long id, String name, String description, Diet diet) {
        return repository.update(id, new Victual(id, name, description, diet))
            .orElseThrow(() -> new NoSuchElementException("Viktualie nicht gefunden: " + id));
    }
}
