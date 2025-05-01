package com.example.GymService.service;

import com.example.GymService.dto.TrainerDto;
import com.example.GymService.exception.ResourceNotFoundException;
import com.example.GymService.model.Trainer;
import com.example.GymService.repository.TrainerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerServiceTest {
    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private TrainerService trainerService;

    private Trainer trainer;
    private TrainerDto trainerDto;

    @BeforeEach
    void setUp() {
        trainer = Trainer.builder()
                .id(1L)
                .name("John Doe")
                .specialization("Weightlifting")
                .experienceYears(5)
                .build();

        trainerDto = TrainerDto.builder()
                .name("John Doe")
                .specialization("Weightlifting")
                .experienceYears(5)
                .build();
    }

    @Test
    void getAllTrainers() {
        when(trainerRepository.findAll()).thenReturn(List.of(trainer));

        List<TrainerDto> result = trainerService.getAllTrainers();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(trainerRepository, times(1)).findAll();
    }

    @Test
    void getTrainerById() {
        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));

        TrainerDto result = trainerService.getTrainerById(1L);

        assertEquals("John Doe", result.getName());
        verify(trainerRepository, times(1)).findById(1L);
    }

    @Test
    void getTrainerByIdNotFound() {
        when(trainerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> trainerService.getTrainerById(1L));
        verify(trainerRepository, times(1)).findById(1L);
    }

    @Test
    void createTrainer() {
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);

        TrainerDto result = trainerService.createTrainer(trainerDto);

        assertEquals("John Doe", result.getName());
        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }

    @Test
    void updateTrainer() {
        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));
        when(trainerRepository.save(any(Trainer.class))).thenReturn(trainer);

        TrainerDto result = trainerService.updateTrainer(1L, trainerDto);

        assertEquals("John Doe", result.getName());
        verify(trainerRepository, times(1)).findById(1L);
        verify(trainerRepository, times(1)).save(any(Trainer.class));
    }

    @Test
    void deleteTrainer() {
        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));

        trainerService.deleteTrainer(1L);

        verify(trainerRepository, times(1)).findById(1L);
        verify(trainerRepository, times(1)).delete(trainer);
    }
}
