package com.example.GymService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutDto {
    private Long id;
    private Long trainerId;
    private Long athleteId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String workoutType;
}
