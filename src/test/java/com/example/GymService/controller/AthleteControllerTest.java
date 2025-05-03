package com.example.GymService.controller;

import com.example.GymService.dto.AthleteDto;
import com.example.GymService.service.AthleteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AthleteController.class)
class AthleteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AthleteService athleteService;

    @Test
    void shouldGetAllAthletes() throws Exception {
        AthleteDto athleteDto = new AthleteDto();
        athleteDto.setName("Test Athlete");

        Mockito.when(athleteService.getAllAthletes()).thenReturn(Collections.singletonList(athleteDto));

        mockMvc.perform(get("/api/athletes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Athlete"));
    }

    @Test
    void shouldCreateAthlete() throws Exception {
        AthleteDto athleteDto = new AthleteDto();
        athleteDto.setName("New Athlete");
        athleteDto.setEmail("new@example.com");

        AthleteDto savedDto = new AthleteDto();
        savedDto.setId(1L);
        savedDto.setName("New Athlete");
        savedDto.setEmail("new@example.com");

        Mockito.when(athleteService.createAthlete(Mockito.any(AthleteDto.class))).thenReturn(savedDto);

        mockMvc.perform(post("/api/athletes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(athleteDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Athlete"));
    }

    @Test
    void shouldGetAthleteById() throws Exception {
        AthleteDto athleteDto = new AthleteDto();
        athleteDto.setId(1L);
        athleteDto.setName("Test Athlete");

        Mockito.when(athleteService.getAthleteById(1L)).thenReturn(athleteDto);

        mockMvc.perform(get("/api/athletes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Athlete"));
    }
}