package com.example.demo.UnitTests;
import com.example.demo.models.dtos.ExerciseDTO;
import com.example.demo.models.entities.Exercise;
import com.example.demo.repositories.ExerciseRepository;
import com.example.demo.services.ExerciseMapper;
import com.example.demo.services.ExerciseService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ExerciseServiceTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private ExerciseMapper exerciseMapper;

    @InjectMocks
    private ExerciseService exerciseService;

    @Test
    public void testCreateExercise() {
        // Create a sample ExerciseDTO for testing
        ExerciseDTO inputDTO = new ExerciseDTO();
        inputDTO.setName("Test Exercise");
        inputDTO.setSets(3);
        inputDTO.setReps(12);
        inputDTO.setWeight(20.0);

        // Mock the behavior of ExerciseMapper.toEntity
        Exercise mockedEntity = new Exercise();
        mockedEntity.setId(1L); // Simulate ID generation by the database
        when(exerciseMapper.toEntity(any(ExerciseDTO.class))).thenReturn(mockedEntity);

        // Mock the behavior of ExerciseRepository.save
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(mockedEntity);

        // Mock the behavior of ExerciseMapper.toDTO
        when(exerciseMapper.toDTO(mockedEntity)).thenReturn(new ExerciseDTO()); // Ensure it returns a non-null value

        // Call the method to be tested
        ExerciseDTO resultDTO = exerciseService.createExercise(inputDTO);

        // Verify that ExerciseMapper.toDTO and ExerciseRepository.save were called
        verify(exerciseMapper).toDTO(mockedEntity);
        verify(exerciseRepository).save(mockedEntity);

        // Assertions based on the specific fields of ExerciseDTO
        assertNotNull(resultDTO, "Result DTO should not be null");
        assertEquals(inputDTO.getName(), resultDTO.getName(), "Name should match");
        assertEquals(inputDTO.getSets(), resultDTO.getSets(), "Sets should match");
        assertEquals(inputDTO.getReps(), resultDTO.getReps(), "Reps should match");
        assertEquals(inputDTO.getWeight(), resultDTO.getWeight(), "Weight should match");
    }
}