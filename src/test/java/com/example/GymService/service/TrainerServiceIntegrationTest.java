package com.example.GymService.service;

import com.example.GymService.dto.TrainerDto;
import com.example.GymService.model.Trainer;
import com.example.GymService.repository.TrainerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class TrainerServiceIntegrationTest {

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private TrainerRepository trainerRepository;

    @AfterEach
    void tearDown() {
        trainerRepository.deleteAll();
    }

    @Test
    void shouldCreateAndRetrieveTrainer() {
        TrainerDto trainerDto = new TrainerDto();
        trainerDto.setName("Test Trainer");
        trainerDto.setSpecialization("Yoga");
        trainerDto.setExperienceYears(5);

        TrainerDto saved = trainerService.createTrainer(trainerDto);
        TrainerDto retrieved = trainerService.getTrainerById(saved.getId());

        assertNotNull(retrieved);
        assertEquals("Test Trainer", retrieved.getName());
        assertEquals("Yoga", retrieved.getSpecialization());
    }

    @Test
    void shouldGetTrainersBySpecialization() {
        Trainer trainer1 = new Trainer();
        trainer1.setName("Trainer 1");
        trainer1.setSpecialization("Yoga");
        trainer1.setExperienceYears(8);
        trainerRepository.save(trainer1);

        Trainer trainer2 = new Trainer();
        trainer2.setName("Trainer 2");
        trainer2.setSpecialization("Weightlifting");
        trainer2.setExperienceYears(30);
        trainerRepository.save(trainer2);

        List<TrainerDto> yogaTrainers = trainerService.getTrainersBySpecialization("Yoga");

        assertEquals(1, yogaTrainers.size());
        assertEquals("Trainer 1", yogaTrainers.get(0).getName());
    }
}
