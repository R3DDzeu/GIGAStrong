package com.example.demo.repositories;


import com.example.demo.models.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    Exercise findByName(String name);

    List<Exercise> findBySets(int sets);

    List<Exercise> findByReps(int reps);


    void deleteByName(String name);
    @Query("SELECT ex FROM Exercise ex WHERE ex.sets = ?1 AND ex.weight = ?2")
    List<Exercise> findBySetsAndWeight(int sets, double weight);

    List<Exercise> findBySetsAndReps(int sets, int reps);
}