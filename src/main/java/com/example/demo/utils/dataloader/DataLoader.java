package com.example.demo.utils.dataloader;

import com.example.demo.book.model.Book;
import com.example.demo.course.Course;
import com.example.demo.student.model.Student;
import com.example.demo.student.repository.StudentRepository;
import com.example.demo.studentidcard.model.StudentIdCard;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final StudentRepository studentRepository;

    @Override
    public void run(String... args) {
        loadRandomStudents();
    }

    private void loadRandomStudents() {
        var faker = new Faker();
        var name = faker.name();
        var number = faker.number();
        var bookFaker = faker.book();
        var students = new ArrayList<Student>();
        for (int i = 0; i < 20; i++) {
            var firstName = name.firstName();
            var lastName = name.lastName();
            var studentIdCard = StudentIdCard.builder()
                    .cardNumber(number.digits(10))
                    .build();
            var student = Student.builder()
                    .firstName(firstName)
                    .lastName(lastName)
                    .age(number.numberBetween(17, 55))
                    .email("%s.%s@email.pl".formatted(firstName, lastName))
                    .studentIdCard(studentIdCard)
                    .build();

            var book = Book.builder()
                    .bookName(bookFaker.title())
                    .createdAt(LocalDateTime.now())
                    .student(student)
                    .build();

            var book2 = Book.builder()
                    .bookName(bookFaker.title())
                    .createdAt(LocalDateTime.now())
                    .student(student)
                    .build();

            student.addBook(book);
            student.addBook(book2);

            students.add(student);

        }

        var course = Course.builder()
                .department("Department 1")
                .name("Course 1")
                .build();

        var course2 = Course.builder()
                .department("Department 2")
                .name("Course 2")
                .build();

        students.get(0)
                .addCourse(course);

        students.get(0)
                .addCourse(course2);

        studentRepository.saveAll(students);
    }

}
