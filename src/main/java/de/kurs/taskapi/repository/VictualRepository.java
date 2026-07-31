package de.kurs.taskapi.repository;

import de.kurs.taskapi.model.Victual;
import de.kurs.taskapi.model.Diet;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 
 * VictualRepository
 */
@Repository
public class VictualRepository {
    private List<Victual> victuals = new ArrayList<>();
    private final AtomicLong sequence = new AtomicLong(0); // generates our consecutive ids

    /**
     * Returns a list of all victuals currently stored
     * 
     * @return List of Victuals currently stored
     */
    public synchronized List<Victual> findAll() {
        return List.copyOf(victuals);
    }

    /**
     * Creates and stores a new victual
     * 
     * @param name Name of the victual
     * @param description Description of the victual
     * @param diet Diet of the victual
     * @return The created and stored victual
     */
    public synchronized Victual save(String name, String description, Diet diet) {
        Long id = sequence.incrementAndGet(); // retrieves next id value
        Victual victual = new Victual(id, name, description, diet);
        victuals.add(victual);
        return victual;
    }

    /**
     * Finds a Victual by its given Id, if it exists
     * 
     * @param id The target victuals id
     * @return The found victual if it exists
     */
    public synchronized Optional<Victual> findById(long id) {
        return victuals.stream().filter(victual -> victual.id() == id).findFirst();
    }

    /**
     * Updates values name, description and/or diet of a victual
     * 
     * @param id The target victual
     * @param victual The victual containing all updated values. May include null values that are not applied
     * @return The updated victual
     */
    public synchronized Optional<Victual> update(long id, Victual victual) {
        for(int i = 0; i < victuals.size(); i++) {
            Victual currentVictual = victuals.get(i);
            if(currentVictual.id() == victual.id()) {
                // filter out null values - @TODO: maybe we should use the dto instead of an actual victual?
                String victualName = victual.name() == null ? currentVictual.name() : victual.name();
                String victualDescription = victual.description() == null ? currentVictual.description() : victual.description();
                Diet victualDiet = victual.diet() == null ? currentVictual.diet() : victual.diet();
                Victual updatedVictual = new Victual(currentVictual.id(), victualName, victualDescription, victualDiet);
                victuals.set(i, updatedVictual);
                return Optional.of(updatedVictual);
            }
        }
        return Optional.empty();
    }

}
