package com.example.demo.studentidcard.repository;

import com.example.demo.studentidcard.model.StudentIdCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentIdCardRepository extends JpaRepository<StudentIdCard, Long> {
}
