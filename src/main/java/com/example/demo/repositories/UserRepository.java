package com.example.demo.repositories;

import com.example.demo.models.entities.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);

    List<User> findByExercises_Name(String exerciseName);

    void deleteById(@NotNull Long userId);

}