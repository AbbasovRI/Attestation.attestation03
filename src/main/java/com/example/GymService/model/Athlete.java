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
@Table(name = "athletes")
@SQLDelete(sql = "UPDATE athletes SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
public class Athlete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Builder.Default
    @Column(nullable = false)
    private Boolean isDeleted = Boolean.FALSE;

    @OneToMany(mappedBy = "athlete")
    private List<Workout> workouts;
}
