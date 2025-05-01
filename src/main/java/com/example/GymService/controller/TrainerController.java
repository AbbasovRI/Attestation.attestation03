package com.example.GymService.controller;

import com.example.GymService.dto.TrainerDto;
import com.example.GymService.service.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
@Tag(name = "Trainer Management", description = "APIs for managing trainers")
public class TrainerController {
    private final TrainerService trainerService;

    @GetMapping
    @Operation(summary = "Get all trainers")
    public ResponseEntity<List<TrainerDto>> getAllTrainers() {
        return ResponseEntity.ok(trainerService.getAllTrainers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get trainer by ID")
    public ResponseEntity<TrainerDto> getTrainerById(@PathVariable Long id) {
        return ResponseEntity.ok(trainerService.getTrainerById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new trainer")
    public ResponseEntity<TrainerDto> createTrainer(@RequestBody TrainerDto trainerDto) {
        return new ResponseEntity<>(trainerService.createTrainer(trainerDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update trainer by ID")
    public ResponseEntity<TrainerDto> updateTrainer(@PathVariable Long id, @RequestBody TrainerDto trainerDto) {
        return ResponseEntity.ok(trainerService.updateTrainer(id, trainerDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete trainer by ID")
    public ResponseEntity<Void> deleteTrainer(@PathVariable Long id) {
        trainerService.deleteTrainer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/specialization/{specialization}")
    @Operation(summary = "Get trainers by specialization")
    public ResponseEntity<List<TrainerDto>> getTrainersBySpecialization(@PathVariable String specialization) {
        return ResponseEntity.ok(trainerService.getTrainersBySpecialization(specialization));
    }
}