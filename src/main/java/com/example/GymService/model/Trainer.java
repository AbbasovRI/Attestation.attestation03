package com.example.GymService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trainers")
@SQLDelete(sql = "UPDATE trainers SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String specialization;

    @Column(nullable = false)
    private Integer experienceYears;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isDeleted = Boolean.FALSE;

    @OneToMany(mappedBy = "trainer")
    private List<Workout> workouts;
}
