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
@Schema(description = "DTO для спортсмена")
public class AthleteDto {
    @Schema(description = "Уникальный идентификатор спортсмена", example = "1")
    private Long id;

    @Schema(description = "Имя спортсмена", example = "John Doe")
    private String name;

    @Schema(description = "Email спортсмена", example = "john@example.com")
    private String email;

    @Schema(description = "Телефон спортсмена", example = "+1234567890")
    private String phone;
}
