package com.example.GymService.dto;

import com.example.GymService.model.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TrainerMapper {
    TrainerMapper INSTANCE = Mappers.getMapper(TrainerMapper.class);

    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "workouts", ignore = true)
    Trainer toEntity(TrainerDto trainerDto);

    TrainerDto toDto(Trainer trainer);
}
