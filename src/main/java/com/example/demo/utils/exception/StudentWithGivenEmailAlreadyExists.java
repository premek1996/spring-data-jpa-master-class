package com.example.demo.utils.exception;

public class StudentWithGivenEmailAlreadyExists extends RuntimeException {

    private static final String MESSAGE = "Student with given email: %s already exists!";

    public StudentWithGivenEmailAlreadyExists(String email) {
        super(MESSAGE.formatted(email));
    }

}
