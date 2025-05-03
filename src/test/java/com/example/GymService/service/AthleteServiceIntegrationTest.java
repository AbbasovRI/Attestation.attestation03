package com.example.GymService.service;

import com.example.GymService.dto.AthleteDto;
import com.example.GymService.model.Athlete;
import com.example.GymService.repository.AthleteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AthleteServiceIntegrationTest {

    @Autowired
    private AthleteService athleteService;

    @Autowired
    private AthleteRepository athleteRepository;

    @AfterEach
    void tearDown() {
        athleteRepository.deleteAll();
    }

    @Test
    void shouldCreateAndRetrieveAthlete() {
        AthleteDto athleteDto = new AthleteDto();
        athleteDto.setName("Test Athlete");
        athleteDto.setEmail("test@example.com");
        athleteDto.setPhone("1234567890");

        AthleteDto saved = athleteService.createAthlete(athleteDto);
        AthleteDto retrieved = athleteService.getAthleteById(saved.getId());

        assertNotNull(retrieved);
        assertEquals("Test Athlete", retrieved.getName());
        assertEquals("test@example.com", retrieved.getEmail());
    }

    @Test
    void shouldUpdateAthlete() {
        Athlete athlete = new Athlete();
        athlete.setName("Initial Name");
        athlete.setEmail("initial@example.com");
        athlete.setPhone("1111111111");
        Athlete saved = athleteRepository.save(athlete);

        AthleteDto updateDto = new AthleteDto();
        updateDto.setName("Updated Name");
        updateDto.setEmail("updated@example.com");
        updateDto.setPhone("2222222222");

        AthleteDto updated = athleteService.updateAthlete(saved.getId(), updateDto);

        assertEquals("Updated Name", updated.getName());
        assertEquals("updated@example.com", updated.getEmail());
    }

    @Test
    void shouldGetAllAthletes() {
        Athlete athlete1 = new Athlete();
        athlete1.setName("Athlete 1");
        athlete1.setEmail("abrakadabra1@gmail.com");
        athlete1.setPhone("88005553535");
        athleteRepository.save(athlete1);

        Athlete athlete2 = new Athlete();
        athlete2.setName("Athlete 2");
        athlete2.setEmail("abrakadabra2@gmail.com");
        athlete2.setPhone("88005553536");
        athleteRepository.save(athlete2);

        List<AthleteDto> athletes = athleteService.getAllAthletes();

        assertEquals(2, athletes.size());
    }
}
