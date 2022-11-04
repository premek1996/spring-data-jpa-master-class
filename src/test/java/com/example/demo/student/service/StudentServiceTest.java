package com.example.demo.student.service;

import com.example.demo.student.dto.AddStudentDTO;
import com.example.demo.student.model.Student;
import com.example.demo.student.repository.StudentRepository;
import com.example.demo.utils.exception.StudentWithGivenEmailAlreadyExists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    @DisplayName("tt should throw exception when student with given email already exists")
    void test1() {
        when(studentRepository.findStudentByEmail(any()))
                .thenReturn(Optional.of(mock(Student.class)));

        assertThatThrownBy(() -> studentService.addStudent(mock(AddStudentDTO.class)))
                .isInstanceOf(StudentWithGivenEmailAlreadyExists.class);

        verify(studentRepository, times(1))
                .findStudentByEmail(any());

        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    @DisplayName("it should add student")
    void test2() {
        when(studentRepository.findStudentByEmail(any()))
                .thenReturn(Optional.empty());

        when(studentRepository.save(any()))
                .thenReturn(Student.builder()
                        .id(1L)
                        .age(15)
                        .email("email@interia.pl")
                        .firstName("firstName")
                        .lastName("lastName")
                        .build());

        assertThatNoException().isThrownBy(() -> studentService.addStudent(mock(AddStudentDTO.class)));

        verify(studentRepository, times(1))
                .findStudentByEmail(any());

        verify(studentRepository, times(1))
                .save(any());

        verifyNoMoreInteractions(studentRepository);
    }

}