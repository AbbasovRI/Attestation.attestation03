package com.example.GymService.service;

import com.example.GymService.dto.TrainerDto;
import com.example.GymService.dto.TrainerMapper;
import com.example.GymService.exception.ResourceNotFoundException;
import com.example.GymService.model.Trainer;
import com.example.GymService.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.GymService.dto.WorkoutDto;
import com.example.GymService.dto.WorkoutMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerService {
    private final TrainerRepository trainerRepository;

    @Transactional(readOnly = true)
    public List<TrainerDto> getAllTrainers() {
        return trainerRepository.findAll().stream()
                .map(TrainerMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TrainerDto getTrainerById(Long id) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with id: " + id));
        return TrainerMapper.INSTANCE.toDto(trainer);
    }

    @Transactional
    public TrainerDto createTrainer(TrainerDto trainerDto) {
        Trainer trainer = TrainerMapper.INSTANCE.toEntity(trainerDto);
        Trainer savedTrainer = trainerRepository.save(trainer);
        return TrainerMapper.INSTANCE.toDto(savedTrainer);
    }

    @Transactional
    public TrainerDto updateTrainer(Long id, TrainerDto trainerDto) {
        Trainer existingTrainer = trainerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with id: " + id));

        existingTrainer.setName(trainerDto.getName());
        existingTrainer.setSpecialization(trainerDto.getSpecialization());
        existingTrainer.setExperienceYears(trainerDto.getExperienceYears());

        Trainer updatedTrainer = trainerRepository.save(existingTrainer);
        return TrainerMapper.INSTANCE.toDto(updatedTrainer);
    }

    @Transactional
    public void deleteTrainer(Long id) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with id: " + id));
        trainerRepository.delete(trainer);
    }

    @Transactional(readOnly = true)
    public List<TrainerDto> getTrainersBySpecialization(String specialization) {
        return trainerRepository.findAllBySpecialization(specialization).stream()
                .map(TrainerMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }
}
