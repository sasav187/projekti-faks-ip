package com.example.internship.model;

import jakarta.persistence.*; 
import lombok.*;

import java.time.*;

import com.example.internship.model.enums.EvaluatorRole;

@Entity
@Table(name = "evaluation",
       uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "internship_id", "evaluatorRole"})
})
@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    private Internship internship;

    @Enumerated(EnumType.STRING)
    private EvaluatorRole evaluatorRole;

    private Integer grade;

    @Lob
    private String comment;

    private LocalDateTime evaluationDate;
}