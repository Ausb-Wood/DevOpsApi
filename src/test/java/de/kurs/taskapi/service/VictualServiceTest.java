package de.kurs.taskapi.service;

import de.kurs.taskapi.model.Victual;
import de.kurs.taskapi.model.Diet;
import de.kurs.taskapi.repository.VictualRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import static org.assertj.core.api.Assertions.*;


public class VictualServiceTest {
    private final VictualRepository repository = mock(VictualRepository.class);
    private final VictualService service = new VictualService(repository);

    @Test
    void getAllShouldReturnEmptyListWhenNoVictualsExist() {
        when(repository.findAll()).thenReturn(List.of());
        List<Victual> actual = service.getAll();
        assertThat(actual).isEmpty();
        verify(repository).findAll();
    }

    @Test
    void getAllShouldReturnAllVictuals() {
        List<Victual> expected = List.of(
            new Victual(0L, "Bandnudeln", "Bandnudeln", Diet.VEGAN),
            new Victual(1L, "Bandnudeln", "Bandnudeln", Diet.VEGAN)
        );
        when(repository.findAll()).thenReturn(expected);
        List<Victual> actual = service.getAll();
        assertEquals(expected, actual);
        verify(repository).findAll();
    }

    @Test
    void createTrimsTitleAndCreatesVictual() {
        Victual expectedVictual = new Victual(1L, "Bandnudeln", "Bandnudeln", Diet.VEGAN);

        when(repository.save(any(), any(), any())).thenReturn(expectedVictual);

        Victual victual = service.create("     Bandnudeln", "Bandnudeln", Diet.VEGAN);

        assertEquals(expectedVictual, victual);
        verify(repository).save("Bandnudeln", "Bandnudeln", Diet.VEGAN);
    }

    @Test
    void findByIdOnNonExistingIdThrowsException() {
        when(repository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.findById(0));
        verify(repository).findById(0);
    }
    
    @Test
    void findByIdReturnsCorrectElement() {
        long targetId = 2L;
        Victual expectedVictual = new Victual(targetId, "Bandnudels", "Mit Ei", Diet.VEGETARIAN);
        when(repository.findById(targetId)).thenReturn(Optional.of(expectedVictual));
    
        Victual actualVictual = service.findById(targetId);

        assertEquals(actualVictual, expectedVictual);
        verify(repository).findById(targetId);
    }

    @Test
    void updateOnNonExistingIdThrowsException() {
        when(repository.update(any(Long.class), any())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.update(0L, "", "", null));
        verify(repository).update(0, new Victual(0L, "", "", null));
    }

    @Test
    void updateCreatesUpdatedVictual() {
        Victual expected = new Victual(0L, "", "", Diet.VEGAN);
        when(repository.update(any(Long.class), any())).thenReturn(Optional.of(expected));
        Victual actual = service.update(0, "", "", Diet.VEGAN);
        assertEquals(expected, actual);
        verify(repository).update(0L, expected);

    }
}
