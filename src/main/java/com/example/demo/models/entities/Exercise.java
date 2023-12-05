package com.example.demo.models.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "exercises")
@Getter
@Setter
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;


    @NotBlank
    @Column(name="name")
    private String name;

    @Column(name="sets")
    @Positive
    private int sets;


    @Column(name="reps")
    @Positive
    private int reps;

    @Column(name="weight")
    @Positive
    private double weight;

    @ManyToMany(mappedBy = "exercises")
    private Set<User> users = new HashSet<>();
}
