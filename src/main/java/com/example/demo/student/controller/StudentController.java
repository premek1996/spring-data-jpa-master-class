package com.example.demo.student.controller;

import com.example.demo.student.dto.AddStudentDTO;
import com.example.demo.student.dto.StudentDTO;
import com.example.demo.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<Page<StudentDTO>> getStudents(@RequestParam int page,
                                                        @RequestParam int size,
                                                        @RequestParam String fieldName) {

        return ResponseEntity.ok(studentService.getStudents(page, size, fieldName));
    }

    @PostMapping
    public ResponseEntity<StudentDTO> addStudent(@Valid @RequestBody AddStudentDTO addStudentDTO) {
        var studentDTO = studentService.addStudent(addStudentDTO);
        return new ResponseEntity<>(studentDTO, HttpStatus.CREATED);
    }

}
