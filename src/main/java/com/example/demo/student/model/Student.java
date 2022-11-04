package com.example.demo.student.model;

import com.example.demo.book.model.Book;
import com.example.demo.course.Course;
import com.example.demo.enrolment.Enrolment;
import com.example.demo.studentidcard.model.StudentIdCard;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "id")
@Entity(name = "Student")
@Table(
        name = "student",
        uniqueConstraints = {
                @UniqueConstraint(name = "student_email_unique", columnNames = "email")
        }
)
public class Student {

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "first_name",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String firstName;

    @Column(
            name = "last_name",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String lastName;

    @Column(
            name = "email",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String email;


    @Column(
            name = "age",
            nullable = false
    )
    private Integer age;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id_card_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "student_id_card_id_fk"))
    private StudentIdCard studentIdCard;

    @OneToMany(
            mappedBy = "student",
            orphanRemoval = true,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REMOVE
            }
    )
    @Builder.Default
    private List<Book> books = new ArrayList<>();

    @OneToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REMOVE
            },
            mappedBy = "student"
    )
    @Builder.Default
    private List<Enrolment> enrolments = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
        book.setStudent(this);
    }

    public void deleteBook(Book book) {
        books.remove(book);
        book.setStudent(null);
    }

    public void addCourse(Course course) {
        enrolments.add(new Enrolment(this, course));
    }

    public void deleteCourse(Course course) {
        enrolments.remove(new Enrolment(this, course));
    }

}
