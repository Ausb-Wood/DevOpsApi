package de.kurs.taskapi.controller;

import de.kurs.taskapi.model.Victual;
import de.kurs.taskapi.model.Diet;
import de.kurs.taskapi.service.VictualService;
import jakarta.validation.Valid; // Startet die Prüfung der Eingabedaten.
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 
 * VictualController
 */
@RestController
@RequestMapping("/api/victuals")
public class VictualController {
    private final VictualService service;

    /** Spring übergibt den Service automatisch. */
    public VictualController(VictualService service) {
        this.service = service;
    }

    /**
     * Returns all victuals stored
     * 
     * @return All Victuals currently stored
     */
    @GetMapping // GET /api/victuals
    public List<Victual> getAll() {
        return service.getAll();
    }

    /**
     * Creates a new Victual
     * 
     * @param request The properties of the victual to be created
     * @return The created victual
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Victual createVictual(@Valid @RequestBody CreateVictualRequest request) {
        return service.create(request.name, request.description, request.diet);
    }

    /**
     * Finds a victual by its id and returns it
     * 
     * @param id The id of target victual
     * @return The found victual if it exists
     * @throws NotFoundException If the victual under given id does not exist
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Victual findById(
        @PathVariable long id
    ) {
        return service.findById(id);
    }


    /**
     * Updates victual with given parameters (name, description, diet). Null-values will be ignored.
     * 
     * @param id The id of the target victual
     * @param request The requestparams of the patch request. can include name, description, diet
     * @return The updated victual
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Victual updateVictual(
        @PathVariable long id,
        @RequestBody UpdateVictualRequest request
    ) {
        return service.update(id, request.name, request.description, request.diet);
    }

    /*
     * DTOs 
     */
    public record CreateVictualRequest(
            @NotBlank(message = "Der Titel darf nicht leer sein.")
            @Size(max = 100, message = "Der Titel darf höchstens 100 Zeichen enthalten.")
            String name,
            @Size(max = 255, message = "Die Beschreibung darf höchstens 255 Zeichen enthalten.")
            String description,
            Diet diet
        ) {
    }

    public record UpdateVictualRequest(
        @Size(max = 100, message = "Der Name darf höchstens 100 Zeichen enthalten.")
        String name,
        @Size(max = 255, message = "Die Beschreibung darf höchstens 255 Zeichen enthalten")
        String description,
        Diet diet
    ) {}
}