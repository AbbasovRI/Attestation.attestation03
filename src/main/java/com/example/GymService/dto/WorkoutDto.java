package com.example.GymService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для тренировки")
public class WorkoutDto {
    @Schema(description = "Уникальный идентификатор тренировки", example = "1")
    private Long id;

    @Schema(description = "ID тренера", example = "1")
    private Long trainerId;

    @Schema(description = "ID спортсмена", example = "1")
    private Long athleteId;

    @Schema(description = "Время начала тренировки (ISO 8601)", example = "2024-01-01T10:00:00")
    private LocalDateTime startTime;

    @Schema(description = "Время окончания тренировки (ISO 8601)", example = "2024-01-01T11:00:00")
    private LocalDateTime endTime;

    @Schema(description = "Тип тренировки", example = "Yoga")
    private String workoutType;
}
