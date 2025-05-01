package com.example.GymService.repository;

import com.example.GymService.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findByTrainerIdAndStartTimeBetween(Long trainerId, LocalDateTime start, LocalDateTime end);
    List<Workout> findByAthleteIdAndStartTimeBetween(Long athleteId, LocalDateTime start, LocalDateTime end);
}
