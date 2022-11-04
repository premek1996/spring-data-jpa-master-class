package com.example.demo.student.service;

import com.example.demo.student.dto.AddStudentDTO;
import com.example.demo.student.dto.StudentDTO;
import com.example.demo.student.repository.StudentRepository;
import com.example.demo.utils.exception.StudentWithGivenEmailAlreadyExists;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Page<StudentDTO> getStudents(int page,
                                        int size,
                                        String fieldName) {
        var pageRequest = PageRequest.of(page, size, Sort.by(fieldName));
        return studentRepository.findAll(pageRequest)
                .map(StudentDTO::of);
    }

    public StudentDTO addStudent(AddStudentDTO addStudentDTO) {
        if (studentRepository.findStudentByEmail(addStudentDTO.getEmail())
                .isPresent()) {
            throw new StudentWithGivenEmailAlreadyExists(addStudentDTO.getEmail());
        }
        var student = studentRepository.save(addStudentDTO.toStudent());
        return StudentDTO.of(student);
    }

}
