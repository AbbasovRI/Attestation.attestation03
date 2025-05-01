package com.example.GymService.controller;

import com.example.GymService.dto.AthleteDto;
import com.example.GymService.service.AthleteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/athletes")
@RequiredArgsConstructor
@Tag(name = "Athlete Management", description = "APIs for managing athletes")
public class AthleteController {
    private final AthleteService athleteService;

    @GetMapping
    @Operation(summary = "Get all athletes")
    public ResponseEntity<List<AthleteDto>> getAllAthletes() {
        return ResponseEntity.ok(athleteService.getAllAthletes());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get athlete by ID")
    public ResponseEntity<AthleteDto> getAthleteById(@PathVariable Long id) {
        return ResponseEntity.ok(athleteService.getAthleteById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new athlete")
    public ResponseEntity<AthleteDto> createAthlete(@RequestBody AthleteDto athleteDto) {
        return new ResponseEntity<>(athleteService.createAthlete(athleteDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update athlete by ID")
    public ResponseEntity<AthleteDto> updateAthlete(@PathVariable Long id, @RequestBody AthleteDto athleteDto) {
        return ResponseEntity.ok(athleteService.updateAthlete(id, athleteDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete athlete by ID")
    public ResponseEntity<Void> deleteAthlete(@PathVariable Long id) {
        athleteService.deleteAthlete(id);
        return ResponseEntity.noContent().build();
    }
}
