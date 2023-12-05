package com.example.demo.services;

import com.example.demo.models.dtos.UserDTO;
import com.example.demo.models.entities.Exercise;
import com.example.demo.models.entities.User;
import com.example.demo.repositories.ExerciseRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ExerciseRepository exerciseRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper, ExerciseRepository exerciseRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.exerciseRepository = exerciseRepository;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        Set<Exercise> exercises = getExercisesByIds(userDTO.getExerciseIds());
        user.setExercises(exercises);

        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }
    @Transactional
    public UserDTO associateExercises(Long userId, List<Long> exerciseIds) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<Exercise> exercises = exerciseRepository.findAllById(exerciseIds);
        existingUser.getExercises().addAll(exercises);

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDTO(updatedUser);
    }

    @Transactional
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(userDTO.getName());
        existingUser.setPassword(userDTO.getPassword());

        Set<Exercise> exercises = getExercisesByIds(userDTO.getExerciseIds());
        existingUser.setExercises(exercises);

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDTO(updatedUser);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    private Set<Exercise> getExercisesByIds(Set<Long> exerciseIds) {
        if (exerciseIds == null) {
            return new HashSet<>();    //added because IDs cannot be null
        }
        return new HashSet<>(exerciseRepository.findAllById(exerciseIds));
    }
}