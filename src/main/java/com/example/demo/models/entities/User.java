package com.example.demo.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
@Getter
@Setter


    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="id")
        private Long id;

        @NotBlank
        @Column(name ="name")
        private String name;

        @NotBlank
        @Column(name = "password")
        private String password;

//        private int age;
//        private String gender;
//        private String experience;
//
        @ManyToMany
        @JoinTable(name = "user_exercise", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "exercise_id"))
        private Set<Exercise> exercises = new HashSet<>();

    }

