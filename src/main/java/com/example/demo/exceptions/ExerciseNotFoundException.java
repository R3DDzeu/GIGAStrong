package com.example.demo.exceptions;

public class ExerciseNotFoundException extends RuntimeException {
    public ExerciseNotFoundException(String message) {
        super(message);
    }
}