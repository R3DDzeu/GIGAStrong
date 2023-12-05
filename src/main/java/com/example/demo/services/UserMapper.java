package com.example.demo.services;

import com.example.demo.models.dtos.UserDTO;
import com.example.demo.models.entities.Exercise;
import com.example.demo.models.entities.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .exerciseIds(mapExerciseIds(user.getExercises()))
                .build();
    }

    public User toEntity(UserDTO userDTO) {
        return User.builder()
                .name(userDTO.getName())
                .password(userDTO.getPassword())
                .exercises(mapExercises(userDTO.getExerciseIds()))
                .build();
    }

    private Set<Long> mapExerciseIds(Set<Exercise> exercises) {
        return exercises.stream()
                .filter(Objects::nonNull)
                .map(Exercise::getId)
                .collect(Collectors.toSet());
    }

    private Set<Exercise> mapExercises(Set<Long> exerciseIds) {
        if (exerciseIds == null) {
            return Collections.emptySet();
        }

        return exerciseIds.stream()
                .map(id -> {
                    Exercise exercise = new Exercise();
                    exercise.setId(id);
                    return exercise;
                })
                .collect(Collectors.toSet());
    }
}