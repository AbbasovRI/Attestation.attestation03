package com.example.GymService.service;

import com.example.GymService.dto.WorkoutDto;
import com.example.GymService.dto.WorkoutMapper;
import com.example.GymService.exception.ResourceNotFoundException;
import com.example.GymService.model.Athlete;
import com.example.GymService.model.Trainer;
import com.example.GymService.model.Workout;
import com.example.GymService.repository.AthleteRepository;
import com.example.GymService.repository.TrainerRepository;
import com.example.GymService.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final TrainerRepository trainerRepository;
    private final AthleteRepository athleteRepository;

    @Transactional(readOnly = true)
    public List<WorkoutDto> getAllWorkouts() {
        return workoutRepository.findAll().stream()
                .map(WorkoutMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public WorkoutDto getWorkoutById(Long id) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found with id: " + id));
        return WorkoutMapper.INSTANCE.toDto(workout);
    }

    @Transactional
    public WorkoutDto createWorkout(WorkoutDto workoutDto) {
        Trainer trainer = trainerRepository.findById(workoutDto.getTrainerId())
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with id: " + workoutDto.getTrainerId()));

        Athlete athlete = athleteRepository.findById(workoutDto.getAthleteId())
                .orElseThrow(() -> new ResourceNotFoundException("Athlete not found with id: " + workoutDto.getAthleteId()));

        Workout workout = WorkoutMapper.INSTANCE.toEntity(workoutDto);
        workout.setTrainer(trainer);
        workout.setAthlete(athlete);

        Workout savedWorkout = workoutRepository.save(workout);
        return WorkoutMapper.INSTANCE.toDto(savedWorkout);
    }

    @Transactional
    public WorkoutDto updateWorkout(Long id, WorkoutDto workoutDto) {
        Workout existingWorkout = workoutRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found with id: " + id));

        Trainer trainer = trainerRepository.findById(workoutDto.getTrainerId())
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with id: " + workoutDto.getTrainerId()));

        Athlete athlete = athleteRepository.findById(workoutDto.getAthleteId())
                .orElseThrow(() -> new ResourceNotFoundException("Athlete not found with id: " + workoutDto.getAthleteId()));

        existingWorkout.setTrainer(trainer);
        existingWorkout.setAthlete(athlete);
        existingWorkout.setStartTime(workoutDto.getStartTime());
        existingWorkout.setEndTime(workoutDto.getEndTime());
        existingWorkout.setWorkoutType(workoutDto.getWorkoutType());

        Workout updatedWorkout = workoutRepository.save(existingWorkout);
        return WorkoutMapper.INSTANCE.toDto(updatedWorkout);
    }

    @Transactional
    public void deleteWorkout(Long id) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found with id: " + id));
        workout.setIsDeleted(true);
        workoutRepository.save(workout);
    }

    @Transactional(readOnly = true)
    public List<WorkoutDto> getTrainerSchedule(Long trainerId, LocalDateTime start, LocalDateTime end) {
        return workoutRepository.findByTrainerIdAndStartTimeBetween(trainerId, start, end).stream()
                .map(WorkoutMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<WorkoutDto> getAthleteSchedule(Long athleteId, LocalDateTime start, LocalDateTime end) {
        return workoutRepository.findByAthleteIdAndStartTimeBetween(athleteId, start, end).stream()
                .map(WorkoutMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }
}
