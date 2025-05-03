package com.example.GymService.controller;

import com.example.GymService.dto.AthleteDto;
import com.example.GymService.service.AthleteService;
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
@RequestMapping("/api/athletes")
@RequiredArgsConstructor
@Tag(name = "Athlete Management", description = "API для управления спортсменами")
public class AthleteController {
    private final AthleteService athleteService;

    @GetMapping
    @Operation(summary = "Получить всех спортсменов", description = "Возвращает список всех зарегистрированных спортсменов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное получение списка спортсменов")
    })
    public ResponseEntity<List<AthleteDto>> getAllAthletes() {
        return ResponseEntity.ok(athleteService.getAllAthletes());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить  по ID", description = "Возвращает данные спортсмена по его идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Спортсмен найден"),
            @ApiResponse(responseCode = "404", description = "Спортсмен не найден")
    })
    public ResponseEntity<AthleteDto> getAthleteById(@PathVariable Long id) {
        return ResponseEntity.ok(athleteService.getAthleteById(id));
    }

    @PostMapping
    @Operation(summary = "Создать нового спортсмена", description = "Создает нового спортсмена на основе переданных данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Спортсмен успешно создан"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных")
    })
    public ResponseEntity<AthleteDto> createAthlete(@RequestBody @Valid AthleteDto athleteDto) {
        return new ResponseEntity<>(athleteService.createAthlete(athleteDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные спортсмена", description = "Обновляет информацию о существующем спортсмене")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные спортсмена успешно обновлены"),
            @ApiResponse(responseCode = "404", description = "Спортсмен не найден")
    })
    public ResponseEntity<AthleteDto> updateAthlete(@PathVariable Long id, @RequestBody @Valid AthleteDto athleteDto) {
        return ResponseEntity.ok(athleteService.updateAthlete(id, athleteDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить спортсмена", description = "Помечает спортсмена как удаленного")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Спортсмен успешно удален"),
            @ApiResponse(responseCode = "404", description = "Спортсмен не найден")
    })
    public ResponseEntity<Void> deleteAthlete(@PathVariable Long id) {
        athleteService.deleteAthlete(id);
        return ResponseEntity.noContent().build();
    }
}
