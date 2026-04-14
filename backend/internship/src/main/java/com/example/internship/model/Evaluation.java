package com.example.internship.model;

import jakarta.persistence.*; 
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.*;

import com.example.internship.model.enums.EvaluatorRole;

@Entity
@Table(name = "evaluation",
       uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "internship_id", "evaluator_role"})
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
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "internship_id", nullable = false)
    private Internship internship;

    @Enumerated(EnumType.STRING)
    @Column(name = "evaluator_role")
    private EvaluatorRole evaluatorRole;

    @Min(5)
    @Max(10)
    private Integer grade;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "evaluation_date")
    private LocalDateTime evaluationDate;
}