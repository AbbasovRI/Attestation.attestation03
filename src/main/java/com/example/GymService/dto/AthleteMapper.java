package com.example.GymService.dto;

import com.example.GymService.model.Athlete;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AthleteMapper {
    AthleteMapper INSTANCE = Mappers.getMapper(AthleteMapper.class);

    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "workouts", ignore = true)
    Athlete toEntity(AthleteDto athleteDto);

    AthleteDto toDto(Athlete athlete);
}
