package com.example.GymService.dto;

import com.example.GymService.model.Workout;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WorkoutMapper {
    WorkoutMapper INSTANCE = Mappers.getMapper(WorkoutMapper.class);

    @Mapping(source = "trainer.id", target = "trainerId")
    @Mapping(source = "athlete.id", target = "athleteId")
    WorkoutDto toDto(Workout workout);

    @Mapping(target = "trainer", ignore = true)
    @Mapping(target = "athlete", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    Workout toEntity(WorkoutDto workoutDto);
}
