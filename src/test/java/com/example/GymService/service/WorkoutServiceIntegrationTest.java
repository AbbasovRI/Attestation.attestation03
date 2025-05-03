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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class WorkoutServiceIntegrationTest {

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private AthleteRepository athleteRepository;

    private Trainer testTrainer;
    private Athlete testAthlete;
    private LocalDateTime now = LocalDateTime.now();
    private LocalDateTime future = now.plusHours(1);

    @BeforeEach
    void setUp() {
        testTrainer = Trainer.builder()
                .name("John")
                .specialization("Fitness")
                .experienceYears(10)
                .build();
        testTrainer = trainerRepository.save(testTrainer);

        testAthlete = Athlete.builder()
                .name("Alice")
                .email("innopolis@mail.ru")
                .phone("228322")
                .build();
        testAthlete = athleteRepository.save(testAthlete);
    }

    @Test
    void testCreateWorkout() {
        WorkoutDto dto = WorkoutDto.builder()
                .trainerId(testTrainer.getId())
                .athleteId(testAthlete.getId())
                .startTime(now)
                .endTime(future)
                .workoutType("Cardio")
                .build();

        WorkoutDto created = workoutService.createWorkout(dto);
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();
        assertThat(created.getTrainerId()).isEqualTo(testTrainer.getId());
        assertThat(created.getAthleteId()).isEqualTo(testAthlete.getId());
    }

    @Test
    void testGetWorkoutById() {
        Workout saved = workoutRepository.save(Workout.builder()
                .trainer(testTrainer)
                .athlete(testAthlete)
                .startTime(now)
                .endTime(future)
                .workoutType("Yoga")
                .build());

        WorkoutDto found = workoutService.getWorkoutById(saved.getId());
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(saved.getId());
    }

    @Test
    void testGetWorkoutById_NotFound() {
        assertThrows(ResourceNotFoundException.class, () -> {
            workoutService.getWorkoutById(999L);
        });
    }

    @Test
    void testUpdateWorkout() {
        Workout saved = workoutRepository.save(Workout.builder()
                .trainer(testTrainer)
                .athlete(testAthlete)
                .startTime(now)
                .endTime(future)
                .workoutType("Yoga")
                .build());

        WorkoutDto updateDto = WorkoutDto.builder()
                .trainerId(testTrainer.getId())
                .athleteId(testAthlete.getId())
                .startTime(now.plusDays(1))
                .endTime(future.plusDays(1))
                .workoutType("Strength")
                .build();

        WorkoutDto updated = workoutService.updateWorkout(saved.getId(), updateDto);
        assertThat(updated.getWorkoutType()).isEqualTo("Strength");
        assertThat(updated.getStartTime()).isEqualTo(updateDto.getStartTime());
    }

    @Test
    void testDeleteWorkout() {
        Workout saved = workoutRepository.save(Workout.builder()
                .trainer(testTrainer)
                .athlete(testAthlete)
                .startTime(now)
                .endTime(future)
                .workoutType("Yoga")
                .build());

        workoutService.deleteWorkout(saved.getId());
        Workout deleted = workoutRepository.findById(saved.getId()).orElse(null);
        assertThat(deleted).isNotNull();
        assertThat(deleted.getIsDeleted()).isTrue();
    }

    @Test
    void testGetTrainerSchedule() {
        workoutRepository.save(Workout.builder()
                .trainer(testTrainer)
                .athlete(testAthlete)
                .startTime(now)
                .endTime(future)
                .workoutType("Yoga")
                .build());

        workoutRepository.save(Workout.builder()
                .trainer(testTrainer)
                .athlete(testAthlete)
                .startTime(now.minusHours(1))
                .endTime(now)
                .workoutType("Pilates")
                .build());

        List<WorkoutDto> schedule = workoutService.getTrainerSchedule(
                testTrainer.getId(),
                now.minusHours(2),
                now.plusHours(2)
        );

        assertThat(schedule).hasSize(2);
    }

    @Test
    void testGetAthleteSchedule() {
        workoutRepository.save(Workout.builder()
                .trainer(testTrainer)
                .athlete(testAthlete)
                .startTime(now)
                .endTime(future)
                .workoutType("Yoga")
                .build());

        workoutRepository.save(Workout.builder()
                .trainer(testTrainer)
                .athlete(testAthlete)
                .startTime(now.minusHours(1))
                .endTime(now)
                .workoutType("Pilates")
                .build());

        List<WorkoutDto> schedule = workoutService.getAthleteSchedule(
                testAthlete.getId(),
                now.minusHours(2),
                now.plusHours(2)
        );

        assertThat(schedule).hasSize(2);
    }
}
