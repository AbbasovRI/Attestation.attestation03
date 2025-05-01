package com.example.GymService.service;

import com.example.GymService.dto.AthleteDto;
import com.example.GymService.exception.ResourceNotFoundException;
import com.example.GymService.model.Athlete;
import com.example.GymService.repository.AthleteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AthleteServiceTest {
    @Mock
    private AthleteRepository athleteRepository;

    @InjectMocks
    private AthleteService athleteService;

    private Athlete athlete;
    private AthleteDto athleteDto;

    @BeforeEach
    void setUp() {
        athlete = Athlete.builder()
                .id(1L)
                .name("Jane Smith")
                .email("jane@example.com")
                .phone("1234567890")
                .build();

        athleteDto = AthleteDto.builder()
                .name("Jane Smith")
                .email("jane@example.com")
                .phone("1234567890")
                .build();
    }

    @Test
    void getAllAthletes() {
        when(athleteRepository.findAll()).thenReturn(List.of(athlete));

        List<AthleteDto> result = athleteService.getAllAthletes();

        assertEquals(1, result.size());
        assertEquals("Jane Smith", result.get(0).getName());
        verify(athleteRepository, times(1)).findAll();
    }

    @Test
    void getAthleteById() {
        when(athleteRepository.findById(1L)).thenReturn(Optional.of(athlete));

        AthleteDto result = athleteService.getAthleteById(1L);

        assertEquals("Jane Smith", result.getName());
        verify(athleteRepository, times(1)).findById(1L);
    }

    @Test
    void getAthleteByIdNotFound() {
        when(athleteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> athleteService.getAthleteById(1L));
        verify(athleteRepository, times(1)).findById(1L);
    }

    @Test
    void createAthlete() {
        when(athleteRepository.save(any(Athlete.class))).thenReturn(athlete);

        AthleteDto result = athleteService.createAthlete(athleteDto);

        assertEquals("Jane Smith", result.getName());
        verify(athleteRepository, times(1)).save(any(Athlete.class));
    }

    @Test
    void updateAthlete() {
        when(athleteRepository.findById(1L)).thenReturn(Optional.of(athlete));
        when(athleteRepository.save(any(Athlete.class))).thenReturn(athlete);

        AthleteDto result = athleteService.updateAthlete(1L, athleteDto);

        assertEquals("Jane Smith", result.getName());
        verify(athleteRepository, times(1)).findById(1L);
        verify(athleteRepository, times(1)).save(any(Athlete.class));
    }

    @Test
    void deleteAthlete() {
        when(athleteRepository.findById(1L)).thenReturn(Optional.of(athlete));

        athleteService.deleteAthlete(1L);

        verify(athleteRepository, times(1)).findById(1L);
        verify(athleteRepository, times(1)).delete(athlete);
    }
}