package com.example.GymService.service;

import com.example.GymService.dto.AthleteDto;
import com.example.GymService.dto.AthleteMapper;
import com.example.GymService.exception.ResourceNotFoundException;
import com.example.GymService.model.Athlete;
import com.example.GymService.repository.AthleteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AthleteService {
    private final AthleteRepository athleteRepository;

    @Transactional(readOnly = true)
    public List<AthleteDto> getAllAthletes() {
        return athleteRepository.findAll().stream()
                .map(AthleteMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AthleteDto getAthleteById(Long id) {
        Athlete athlete = athleteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Athlete not found with id: " + id));
        return AthleteMapper.INSTANCE.toDto(athlete);
    }

    @Transactional
    public AthleteDto createAthlete(AthleteDto athleteDto) {
        Athlete athlete = AthleteMapper.INSTANCE.toEntity(athleteDto);
        Athlete savedAthlete = athleteRepository.save(athlete);
        return AthleteMapper.INSTANCE.toDto(savedAthlete);
    }

    @Transactional
    public AthleteDto updateAthlete(Long id, AthleteDto athleteDto) {
        Athlete existingAthlete = athleteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Athlete not found with id: " + id));

        existingAthlete.setName(athleteDto.getName());
        existingAthlete.setEmail(athleteDto.getEmail());
        existingAthlete.setPhone(athleteDto.getPhone());

        Athlete updatedAthlete = athleteRepository.save(existingAthlete);
        return AthleteMapper.INSTANCE.toDto(updatedAthlete);
    }

    @Transactional
    public void deleteAthlete(Long id) {
        Athlete athlete = athleteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Athlete not found with id: " + id));
        athleteRepository.delete(athlete);
    }
}
