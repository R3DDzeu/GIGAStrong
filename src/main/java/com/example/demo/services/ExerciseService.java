package com.example.demo.services;
import com.example.demo.exceptions.ExerciseNotFoundException;
import com.example.demo.models.dtos.ExerciseDTO;
import com.example.demo.models.entities.Exercise;
import com.example.demo.repositories.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final ExerciseMapper exerciseMapper;

    @Autowired
    public ExerciseService(ExerciseRepository exerciseRepository, ExerciseMapper exerciseMapper) {
        this.exerciseRepository = exerciseRepository;
        this.exerciseMapper = exerciseMapper;
    }

    public List<ExerciseDTO> getExercisesBySetsAndReps(int sets, int reps) {
        List<Exercise> exerciseEntities = exerciseRepository.findBySetsAndReps(sets, reps);
        return mapExerciseEntitiesToDTOs(exerciseEntities);
    }

    public List<ExerciseDTO> getExercisesBySetsAndWeigth(int sets, int weight) {
        List<Exercise> exerciseEntities = exerciseRepository.findBySetsAndWeight(sets, weight);
        return mapExerciseEntitiesToDTOs(exerciseEntities);
    }

    public List<ExerciseDTO> getAllExercises() {
        List<Exercise> exercises = exerciseRepository.findAll();
        return exercises.stream().map(exerciseMapper::toDTO).collect(Collectors.toList());
    }

    public ExerciseDTO getExerciseById(Long exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new ExerciseNotFoundException("Exercise not found with ID: " + exerciseId));
        return exerciseMapper.toDTO(exercise);
    }

    @Transactional
    public ExerciseDTO createExercise(ExerciseDTO exerciseDTO) {
        try {
            Exercise exercise = exerciseMapper.toEntity(exerciseDTO);
            Exercise savedExercise = exerciseRepository.save(exercise);
            return exerciseMapper.toDTO(savedExercise);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create exercise: " + e.getMessage(), e);
        }
    }

    @Transactional
    public ExerciseDTO updateExercise(Long exerciseId, ExerciseDTO exerciseDTO) {
        Exercise existingExercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise not found"));

        existingExercise.setName(exerciseDTO.getName());
        existingExercise.setSets(exerciseDTO.getSets());
        existingExercise.setReps(exerciseDTO.getReps());
        existingExercise.setWeight(exerciseDTO.getWeight());

        Exercise updatedExercise = exerciseRepository.save(existingExercise);
        return exerciseMapper.toDTO(updatedExercise);
    }

    @Transactional
    public void deleteExercise(Long exerciseId) {
        exerciseRepository.deleteById(exerciseId);
    }

    private List<ExerciseDTO> mapExerciseEntitiesToDTOs(List<Exercise> exerciseEntities) {
        return exerciseEntities.stream()
                .map(exerciseMapper::toDTO)
                .collect(Collectors.toList());
    }
}