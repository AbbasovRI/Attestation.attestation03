package com.example.GymService.service;

import com.example.GymService.dto.WorkoutDto;
import com.example.GymService.exception.ResourceNotFoundException;
import com.example.GymService.model.Athlete;
import com.example.GymService.model.Trainer;
import com.example.GymService.model.Workout;
import com.example.GymService.repository.AthleteRepository;
import com.example.GymService.repository.TrainerRepository;
import com.example.GymService.repository.WorkoutRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkoutServiceTest {

    @Mock
    private WorkoutRepository workoutRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private AthleteRepository athleteRepository;

    @InjectMocks
    private WorkoutService workoutService;

    private WorkoutDto workoutDto;
    private Trainer trainer;
    private Athlete athlete;

    @BeforeEach
    void setUp() {
        workoutDto = new WorkoutDto();
        workoutDto.setTrainerId(1L);
        workoutDto.setAthleteId(1L);
        workoutDto.setWorkoutType("Yoga");
        workoutDto.setStartTime(LocalDateTime.now());
        workoutDto.setEndTime(LocalDateTime.now().plusHours(1));

        trainer = new Trainer();
        trainer.setId(1L);
        trainer.setName("John Trainer");

        athlete = new Athlete();
        athlete.setId(1L);
        athlete.setName("Jane Athlete");
    }

    @Test
    void createWorkout_ShouldSuccess() {
        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));
        when(athleteRepository.findById(1L)).thenReturn(Optional.of(athlete));
        when(workoutRepository.save(any(Workout.class))).thenAnswer(invocation -> {
            Workout w = invocation.getArgument(0);
            w.setId(1L);
            return w;
        });

        WorkoutDto result = workoutService.createWorkout(workoutDto);

        assertNotNull(result.getId());
        assertEquals("Yoga", result.getWorkoutType());
        verify(workoutRepository, times(1)).save(any(Workout.class));
    }

    @Test
    void createWorkout_WhenTrainerNotFound_ShouldThrow() {
        when(trainerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                workoutService.createWorkout(workoutDto));
    }

    @Test
    void getTrainerSchedule_ShouldReturnWorkouts() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(1);
        Workout workout = new Workout();
        workout.setId(1L);
        workout.setWorkoutType("Yoga");

        when(workoutRepository.findByTrainerIdAndStartTimeBetween(1L, start, end))
                .thenReturn(Collections.singletonList(workout));

        List<WorkoutDto> result = workoutService.getTrainerSchedule(1L, start, end);

        assertEquals(1, result.size());
        assertEquals("Yoga", result.get(0).getWorkoutType());
    }

    @Test
    void updateWorkout_ShouldSuccess() {
        Workout existing = new Workout();
        existing.setId(1L);

        when(workoutRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));
        when(athleteRepository.findById(1L)).thenReturn(Optional.of(athlete));
        when(workoutRepository.save(any(Workout.class))).thenReturn(existing);

        WorkoutDto result = workoutService.updateWorkout(1L, workoutDto);

        assertNotNull(result);
        verify(workoutRepository, times(1)).save(any(Workout.class));
    }
}
