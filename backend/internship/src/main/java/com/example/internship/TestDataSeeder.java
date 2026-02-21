package com.example.internship;

import com.example.internship.model.*;
import com.example.internship.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TestDataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final CompanyRepository companyRepository;
    private final InternshipRepository internshipRepository;

    public TestDataSeeder(UserRepository userRepository,
                          StudentRepository studentRepository,
                          CompanyRepository companyRepository,
                          InternshipRepository internshipRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.companyRepository = companyRepository;
        this.internshipRepository = internshipRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User studentUser = User.builder()
                    .username("student1")
                    .password("pass")
                    .role(User.Role.STUDENT)
                    .build();
            studentUser = userRepository.save(studentUser);

            Student student = Student.builder()
                    .user(studentUser)
                    .index("2026/001")
                    .faculty("Engineering")
                    .year(3)
                    .build();
            studentRepository.save(student);

            User companyUser = User.builder()
                    .username("acme")
                    .password("pass")
                    .role(User.Role.COMPANY)
                    .build();
            companyUser = userRepository.save(companyUser);

            Company company = Company.builder()
                    .user(companyUser)
                    .name("Acme Ltd")
                    .description("Example company")
                    .active(true)
                    .build();
            company = companyRepository.save(company);

            Internship internship = Internship.builder()
                    .company(company)
                    .title("Backend Intern")
                    .description("Work on Java backend")
                    .technologies("Java, Spring Boot")
                    .period("3 months")
                    .conditions("Paid")
                    .build();
            internshipRepository.save(internship);

            System.out.println("Seeded sample data: student, company, internship");
        }
    }
}
