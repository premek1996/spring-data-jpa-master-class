package com.example.demo.student.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.assertj.core.api.Assertions.assertThat;

class AddStudentDTOTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("is should not fail when dto is correct")
    void test1() {
        var addStudentDTO = AddStudentDTO.builder()
                .age(25)
                .email("email@interia.pl")
                .firstName("firstName")
                .lastName("lastName")
                .build();

        var violations = validator.validate(addStudentDTO);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("it should fail when age is missing")
    void test2() {
        var addStudentDTO = AddStudentDTO.builder()
                .email("email@interia.pl")
                .firstName("firstName")
                .lastName("lastName")
                .build();

        var violations = validator.validate(addStudentDTO);
        assertThat(violations).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(ints = {-5, -2, 0})
    @DisplayName("it should fail when age is not positive")
    void test3(int age) {
        var addStudentDTO = AddStudentDTO.builder()
                .age(age)
                .email("email@interia.pl")
                .firstName("firstName")
                .lastName("lastName")
                .build();

        var violations = validator.validate(addStudentDTO);
        assertThat(violations).isNotEmpty();
    }

    @Test
    @DisplayName("is should fail when email is missing")
    void test4() {
        var addStudentDTO = AddStudentDTO.builder()
                .age(25)
                .firstName("firstName")
                .lastName("lastName")
                .build();

        var violations = validator.validate(addStudentDTO);
        assertThat(violations).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "32453245", "email54", "email.pl"})
    @DisplayName("is should fail when email is not correct")
    void test5(String email) {
        var addStudentDTO = AddStudentDTO.builder()
                .age(25)
                .email(email)
                .firstName("firstName")
                .lastName("lastName")
                .build();

        var violations = validator.validate(addStudentDTO);
        assertThat(violations).isNotEmpty();
    }

    @Test
    @DisplayName("is should fail when first name is missing")
    void test6() {
        var addStudentDTO = AddStudentDTO.builder()
                .age(25)
                .email("email@interia.pl")
                .lastName("lastName")
                .build();

        var violations = validator.validate(addStudentDTO);
        assertThat(violations).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    @DisplayName("is should fail when first name is blank")
    void test7(String firstName) {
        var addStudentDTO = AddStudentDTO.builder()
                .age(25)
                .email("email@interia.pl")
                .firstName(firstName)
                .lastName("lastName")
                .build();

        var violations = validator.validate(addStudentDTO);
        assertThat(violations).isNotEmpty();
    }

    @Test
    @DisplayName("is should fail when last name is missing")
    void test8() {
        var addStudentDTO = AddStudentDTO.builder()
                .age(25)
                .firstName("fistName")
                .email("email@interia.pl")
                .build();

        var violations = validator.validate(addStudentDTO);
        assertThat(violations).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    @DisplayName("is should fail when last name is blank")
    void test9(String lastName) {
        var addStudentDTO = AddStudentDTO.builder()
                .age(25)
                .email("email@interia.pl")
                .firstName("fistName")
                .lastName(lastName)
                .build();

        var violations = validator.validate(addStudentDTO);
        assertThat(violations).isNotEmpty();
    }

}