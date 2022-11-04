package com.example.demo.student.controller;

import com.example.demo.student.dto.AddStudentDTO;
import com.example.demo.student.dto.StudentDTO;
import com.example.demo.student.service.StudentService;
import com.example.demo.utils.exception.StudentWithGivenEmailAlreadyExists;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @MockBean
    private StudentService studentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("it should add new student")
    @SneakyThrows
    void test1() {
        var addStudentDTO = AddStudentDTO.builder()
                .age(15)
                .email("email@interia.pl")
                .firstName("firstName")
                .lastName("lastName")
                .build();

        var studentDTO = StudentDTO.builder()
                .id(1L)
                .age(15)
                .email("email@interia.pl")
                .firstName("firstName")
                .lastName("lastName")
                .build();

        when(studentService.addStudent(addStudentDTO))
                .thenReturn(studentDTO);

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addStudentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("firstName"))
                .andExpect(jsonPath("$.lastName").value("lastName"))
                .andExpect(jsonPath("$.age").value("15"))
                .andExpect(jsonPath("$.email").value("email@interia.pl"));
    }


    @Test
    @DisplayName("it should fail when dto with new student is missing")
    @SneakyThrows
    void test2() {
        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("it should not add new student when student with given email already exists")
    @SneakyThrows
    void test3() {
        var addStudentDTO = AddStudentDTO.builder()
                .age(15)
                .email("email@interia.pl")
                .firstName("firstName")
                .lastName("lastName")
                .build();

        when(studentService.addStudent(addStudentDTO))
                .thenThrow(new StudentWithGivenEmailAlreadyExists(anyString()));
        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addStudentDTO)))
                .andExpect(status().isBadRequest());
    }

}