package com.example.demo.UnitTests;

import com.example.demo.models.entities.Exercise;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ExerciseTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    public void exerciseValidationShouldPass() {
        Exercise exercise = new Exercise();
        exercise.setName("Squats");
        exercise.setSets(3);
        exercise.setReps(10);
        exercise.setWeight(100.0);

        // Validate the exercise object
        assertThat(validator.validate(exercise)).isEmpty();
    }

    @Test
    public void exerciseValidationShouldFailForBlankName() {
        Exercise exercise = new Exercise();
        exercise.setSets(3);
        exercise.setReps(10);
        exercise.setWeight(100.0);

        // Validate the exercise object - it should fail due to the blank name
        assertThat(validator.validate(exercise)).isNotEmpty();
    }

    @Test
    public void exerciseValidationShouldFailForNegativeSets() {
        Exercise exercise = new Exercise();
        exercise.setName("Bench Press");
        exercise.setSets(-1);
        exercise.setReps(12);
        exercise.setWeight(80.0);

        // Validate the exercise object - it should fail due to negative sets
        assertThat(validator.validate(exercise)).isNotEmpty();
    }

    @Test
    public void exerciseValidationShouldFailForNegativeReps() {
        Exercise exercise = new Exercise();
        exercise.setName("Deadlifts");
        exercise.setSets(4);
        exercise.setReps(-8);
        exercise.setWeight(120.0);

        // Validate the exercise object - it should fail due to negative reps
        assertThat(validator.validate(exercise)).isNotEmpty();
    }

    @Test
    public void exerciseValidationShouldFailForNegativeWeight() {
        Exercise exercise = new Exercise();
        exercise.setName("Pull-ups");
        exercise.setSets(3);
        exercise.setReps(10);
        exercise.setWeight(-20.0);

        // Validate the exercise object - it should fail due to negative weight
        assertThat(validator.validate(exercise)).isNotEmpty();
    }

}