package com.example.demo.models.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDTO {
    @Positive
    private Long id;

    @NotNull(message = "Name is required")
    private String name;

    @Positive(message = "Sets must be a positive number")
    private int sets;

    @Positive(message = "Reps must be a positive number")
    private int reps;

    @Positive(message = "Weight must be a positive number")
    private double weight;

}