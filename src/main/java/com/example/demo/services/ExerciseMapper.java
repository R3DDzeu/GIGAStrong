package com.example.demo.services;
import com.example.demo.models.dtos.ExerciseDTO;
import com.example.demo.models.entities.Exercise;
import org.springframework.stereotype.Component;
@Component
public class ExerciseMapper {

    public ExerciseDTO toDTO(Exercise exercise) {
        return ExerciseDTO.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .sets(exercise.getSets())
                .reps(exercise.getReps())
                .weight(exercise.getWeight())
                .build();
    }

    public Exercise toEntity(ExerciseDTO exerciseDTO) {
        return Exercise.builder()
                .id(exerciseDTO.getId())
                .name(exerciseDTO.getName())
                .sets(exerciseDTO.getSets())
                .reps(exerciseDTO.getReps())
                .weight(exerciseDTO.getWeight())
                .build();
    }
}