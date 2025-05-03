package com.example.GymService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для тренера")
public class TrainerDto {
    @Schema(description = "Уникальный идентификатор тренера", example = "1")
    private Long id;

    @Schema(description = "Имя тренера", example = "Jane Smith")
    private String name;

    @Schema(description = "Специализация тренера", example = "Yoga")
    private String specialization;

    @Schema(description = "Опыт работы в годах", example = "5")
    private Integer experienceYears;
}
