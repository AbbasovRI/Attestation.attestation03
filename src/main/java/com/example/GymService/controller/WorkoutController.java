package com.example.GymService.controller;

import com.example.GymService.dto.WorkoutDto;
import com.example.GymService.service.WorkoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Workout Management", description = "API для управления тренировками: создание, обновление, удаление и расписание.")
public class WorkoutController {
    private final WorkoutService workoutService;

    @GetMapping
    @Operation(summary = "Получить все тренировки", description = "Возвращает список всех зарегистрированных тренировок")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка тренировок")
    })
    public ResponseEntity<List<WorkoutDto>> getAllWorkouts() {
        return ResponseEntity.ok(workoutService.getAllWorkouts());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить тренировку по ID", description = "Возвращает данные тренировки по её идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Тренировка найдена"),
            @ApiResponse(responseCode = "404", description = "Тренировка не найдена")
    })
    public ResponseEntity<WorkoutDto> getWorkoutById(@PathVariable Long id) {
        return ResponseEntity.ok(workoutService.getWorkoutById(id));
    }

    @PostMapping
    @Operation(summary = "Создать новую тренировку", description = "Создает новую тренировку на основе переданных данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Тренировка успешно создана"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных")
    })
    public ResponseEntity<WorkoutDto> createWorkout(@RequestBody @Valid WorkoutDto workoutDto) {
        return new ResponseEntity<>(workoutService.createWorkout(workoutDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные тренировки", description = "Обновляет информацию о существующей тренировке")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные тренировки успешно обновлены"),
            @ApiResponse(responseCode = "404", description = "Тренировка не найдена")
    })
    public ResponseEntity<WorkoutDto> updateWorkout(@PathVariable Long id, @RequestBody @Valid WorkoutDto workoutDto) {
        return ResponseEntity.ok(workoutService.updateWorkout(id, workoutDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить тренировку", description = "Помечает тренировку как удаленную")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Тренировка успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Тренировка не найдена")
    })
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/trainer/{trainerId}/schedule")
    @Operation(summary = "Получить расписание тренера", description = "Возвращает список тренировок тренера в заданном диапазоне дат")
    @Parameters({
            @Parameter(name = "trainerId", description = "ID тренера", example = "1"),
            @Parameter(name = "start", description = "Начало периода (ISO 8601)", example = "2024-01-01T10:00:00"),
            @Parameter(name = "end", description = "Конец периода (ISO 8601)", example = "2024-01-02T10:00:00")
    })
    public ResponseEntity<List<WorkoutDto>> getTrainerSchedule(
            @PathVariable Long trainerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(workoutService.getTrainerSchedule(trainerId, start, end));
    }

    @GetMapping("/athlete/{athleteId}/schedule")
    @Operation(summary = "Получить расписание атлета", description = "Возвращает список тренировок атлета в заданном диапазоне дат")
    @Parameters({
            @Parameter(name = "athleteId", description = "ID атлета", example = "1"),
            @Parameter(name = "start", description = "Начало периода (ISO 8601)", example = "2024-01-01T10:00:00"),
            @Parameter(name = "end", description = "Конец периода (ISO 8601)", example = "2024-01-02T10:00:00")
    })
    public ResponseEntity<List<WorkoutDto>> getAthleteSchedule(
            @PathVariable Long athleteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(workoutService.getAthleteSchedule(athleteId, start, end));
    }
}
