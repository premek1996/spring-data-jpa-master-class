package com.example.demo.student.repository;

import com.example.demo.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE s.email = ?1")
    Optional<Student> findStudentByEmail(String email);

    @Query(" SELECT s FROM Student s " +
            " WHERE s.firstName = :firstName and s.age = :age ")
    List<Student> findStudentsByFirstNameEqualsAndAgeEquals(@Param("firstName") String firstName,
                                                            @Param("age") Integer age);

//    @Query("SELECT s FROM Student s WHERE s.firstName = ?1 and s.age = ?2")
//    List<Student> findStudentsByFirstNameEqualsAndAgeEquals(String firstName, Integer age);

//    @Query(
//            value = "SELECT * FROM student WHERE first_name = ?1 and age = ?2",
//            nativeQuery = true
//    )
//    List<Student> findStudentsByFirstNameEqualsAndAgeEquals(String firstName, Integer age);

    @Transactional
    @Modifying
    @Query("DELETE FROM Student s WHERE s.id = :id")
    int deleteStudentById(@Param("id") Long id);

}
