package com.example.GymService.controller;

import com.example.GymService.dto.TrainerDto;
import com.example.GymService.service.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
@RequiredArgsConstructor
@Tag(name = "Trainer Management", description = "API для управления тренерами: создание, обновление, удаление и получение информации.")
public class TrainerController {
    private final TrainerService trainerService;

    @GetMapping
    @Operation(summary = "Получить всех тренеров", description = "Возвращает список всех зарегистрированных тренеров")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка тренеров")
    })
    public ResponseEntity<List<TrainerDto>> getAllTrainers() {
        return ResponseEntity.ok(trainerService.getAllTrainers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить тренера по ID", description = "Возвращает данные тренера по его идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Тренер найден"),
            @ApiResponse(responseCode = "404", description = "Тренер не найден")
    })
    public ResponseEntity<TrainerDto> getTrainerById(@PathVariable Long id) {
        return ResponseEntity.ok(trainerService.getTrainerById(id));
    }

    @PostMapping
    @Operation(summary = "Создать нового тренера", description = "Создает нового тренера на основе переданных данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Тренер успешно создан"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных")
    })
    public ResponseEntity<TrainerDto> createTrainer(@RequestBody @Valid TrainerDto trainerDto) {
        return new ResponseEntity<>(trainerService.createTrainer(trainerDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные тренера", description = "Обновляет информацию о существующем тренере")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные тренера успешно обновлены"),
            @ApiResponse(responseCode = "404", description = "Тренер не найден")
    })
    public ResponseEntity<TrainerDto> updateTrainer(@PathVariable Long id, @RequestBody @Valid TrainerDto trainerDto) {
        return ResponseEntity.ok(trainerService.updateTrainer(id, trainerDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить тренера", description = "Помечает тренера как удаленного")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Тренер успешно удален"),
            @ApiResponse(responseCode = "404", description = "Тренер не найден")
    })
    public ResponseEntity<Void> deleteTrainer(@PathVariable Long id) {
        trainerService.deleteTrainer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/specialization/{specialization}")
    @Operation(summary = "Получить тренеров по специализации", description = "Возвращает список тренеров с заданной специализацией")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Тренеры найдены"),
            @ApiResponse(responseCode = "404", description = "Тренеры не найдены")
    })
    public ResponseEntity<List<TrainerDto>> getTrainersBySpecialization(@PathVariable String specialization) {
        return ResponseEntity.ok(trainerService.getTrainersBySpecialization(specialization));
    }
}