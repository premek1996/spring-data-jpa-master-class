package com.example.demo.enrolment;

import com.example.demo.course.Course;
import com.example.demo.student.model.Student;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table(name = "enrolment")
@Entity(name = "Enrolment")
public class Enrolment {

    @EmbeddedId
    private EnrolmentId id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(
            name = "student_id",
            foreignKey = @ForeignKey(name = "enrolment_student_id_fk")
    )
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id",
            foreignKey = @ForeignKey(name = "enrolment_course_id_fk"))
    private Course course;

    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt;

    public Enrolment(Student student, Course course) {
        this.id = new EnrolmentId();
        this.student = student;
        this.course = course;
        this.createdAt = LocalDateTime.now();
    }

}
