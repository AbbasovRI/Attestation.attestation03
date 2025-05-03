package com.example.GymService.controller;

import com.example.GymService.dto.WorkoutDto;
import com.example.GymService.service.WorkoutService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WorkoutController.class)
class WorkoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WorkoutService workoutService;

    @Test
    void shouldGetTrainerSchedule() throws Exception {
        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setId(1L);
        workoutDto.setWorkoutType("Yoga");

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(1);

        Mockito.when(workoutService.getTrainerSchedule(1L, start, end))
                .thenReturn(Collections.singletonList(workoutDto));

        mockMvc.perform(get("/api/workouts/trainer/1/schedule")
                        .param("start", start.toString())
                        .param("end", end.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].workoutType").value("Yoga"));
    }

    @Test
    void shouldCreateWorkout() throws Exception {
        WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setTrainerId(1L);
        workoutDto.setAthleteId(1L);
        workoutDto.setWorkoutType("Yoga");
        workoutDto.setStartTime(LocalDateTime.now());
        workoutDto.setEndTime(LocalDateTime.now().plusHours(1));

        WorkoutDto savedDto = new WorkoutDto();
        savedDto.setId(1L);
        savedDto.setWorkoutType("Yoga");

        Mockito.when(workoutService.createWorkout(Mockito.any(WorkoutDto.class))).thenReturn(savedDto);

        mockMvc.perform(post("/api/workouts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workoutDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.workoutType").value("Yoga"));
    }
}
