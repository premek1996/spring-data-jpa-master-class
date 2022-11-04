package com.example.demo.student.repository;

import com.example.demo.student.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    @DisplayName("it should find student by email")
    void test1() {
        var student = Student.builder()
                .age(15)
                .email("email")
                .firstName("firstName")
                .lastName("lastName")
                .build();

        var student2 = Student.builder()
                .age(15)
                .email("email2")
                .firstName("firstName")
                .lastName("lastName")
                .build();

        studentRepository.saveAll(List.of(student, student2));

        assertThat(studentRepository.findStudentByEmail("email"))
                .contains(student);
    }

    @Test
    @DisplayName("it should find students by first name and age")
    void test2() {
        var student = Student.builder()
                .age(15)
                .email("email")
                .firstName("firstName")
                .lastName("lastName")
                .build();

        var student2 = Student.builder()
                .age(20)
                .email("email2")
                .firstName("firstName")
                .lastName("lastName")
                .build();

        studentRepository.saveAll(List.of(student, student2));

        assertThat(studentRepository.findStudentsByFirstNameEqualsAndAgeEquals("firstName", 15))
                .containsExactly(student);
    }

}