package com.example.GymService.controller;

import com.example.GymService.dto.WorkoutDto;
import com.example.GymService.service.WorkoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
@Tag(name = "Workout Management", description = "APIs for managing workouts")
public class WorkoutController {
    private final WorkoutService workoutService;

    @GetMapping
    @Operation(summary = "Get all workouts")
    public ResponseEntity<List<WorkoutDto>> getAllWorkouts() {
        return ResponseEntity.ok(workoutService.getAllWorkouts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get workout by ID")
    public ResponseEntity<WorkoutDto> getWorkoutById(@PathVariable Long id) {
        return ResponseEntity.ok(workoutService.getWorkoutById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new workout")
    public ResponseEntity<WorkoutDto> createWorkout(@RequestBody WorkoutDto workoutDto) {
        return new ResponseEntity<>(workoutService.createWorkout(workoutDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update workout by ID")
    public ResponseEntity<WorkoutDto> updateWorkout(@PathVariable Long id, @RequestBody WorkoutDto workoutDto) {
        return ResponseEntity.ok(workoutService.updateWorkout(id, workoutDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete workout by ID")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/trainer/{trainerId}/schedule")
    @Operation(summary = "Get trainer's schedule between dates")
    public ResponseEntity<List<WorkoutDto>> getTrainerSchedule(
            @PathVariable Long trainerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(workoutService.getTrainerSchedule(trainerId, start, end));
    }

    @GetMapping("/athlete/{athleteId}/schedule")
    @Operation(summary = "Get athlete's schedule between dates")
    public ResponseEntity<List<WorkoutDto>> getAthleteSchedule(
            @PathVariable Long athleteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(workoutService.getAthleteSchedule(athleteId, start, end));
    }
}
