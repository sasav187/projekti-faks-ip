package com.example.internship.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "evaluation")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "internship_id", nullable = false)
    private Internship internship;

    private Integer grade;

    @Column(columnDefinition = "TEXT")
    private String comment;

    private LocalDateTime evaluationDate;

    @PrePersist
    protected void onCreate() {
        evaluationDate = LocalDateTime.now();
    }
}
