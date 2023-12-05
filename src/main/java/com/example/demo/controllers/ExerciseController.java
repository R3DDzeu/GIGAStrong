package com.example.demo.controllers;
import com.example.demo.models.dtos.ExerciseDTO;
import com.example.demo.models.entities.Exercise;
import com.example.demo.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {
    private final ExerciseService exerciseService;
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public List<ExerciseDTO> getAllExercises() {
        return exerciseService.getAllExercises();
    }

    @GetMapping("/getExerciseBySetsAndReps")
    public ResponseEntity<List<ExerciseDTO>> getExerciseBySetsAndReps(
            @RequestParam("sets") int sets,
            @RequestParam("reps") int reps) {
        List<ExerciseDTO> exercises = exerciseService.getExercisesBySetsAndReps(sets, reps);

        if (!exercises.isEmpty()) {
            return new ResponseEntity<>(exercises, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getExerciseBySetsAndWeigth")
    public ResponseEntity<List<ExerciseDTO>> getExerciseBySetsAndWeigth(
            @RequestParam("sets") int sets,
            @RequestParam("weigth") int weigth) {
        List<ExerciseDTO> exercises = exerciseService.getExercisesBySetsAndReps(sets, weigth);

        if (!exercises.isEmpty()) {
            return new ResponseEntity<>(exercises, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getExerciseById/{exerciseId}")
    public ResponseEntity<ExerciseDTO> getExerciseById(@PathVariable Long exerciseId) {
        ExerciseDTO exerciseDTO = exerciseService.getExerciseById(exerciseId);
        return ResponseEntity.ok(exerciseDTO);
    }

    @PostMapping
    public ResponseEntity<ExerciseDTO> createExercise(@RequestBody ExerciseDTO exerciseDTO) {
        ExerciseDTO createdExercise = exerciseService.createExercise(exerciseDTO);
        return new ResponseEntity<>(createdExercise, HttpStatus.CREATED);
    }

    @PutMapping("/updateExerciseById/{exerciseId}")
    public ResponseEntity<ExerciseDTO> updateExercise(@PathVariable Long exerciseId, @RequestBody ExerciseDTO exerciseDTO) {
        ExerciseDTO updatedExercise = exerciseService.updateExercise(exerciseId, exerciseDTO);
        return ResponseEntity.ok(updatedExercise);
    }

    @DeleteMapping("/DeleteExerciseById/{exerciseId}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long exerciseId) {
        exerciseService.deleteExercise(exerciseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}