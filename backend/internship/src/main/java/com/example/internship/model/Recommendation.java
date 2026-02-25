package com.example.internship.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "recommendation",
       uniqueConstraints = {
        @UniqueConstraint(columnNames = {"student_id", "internship_id"})
})
@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    private Internship internship;

    private Double score;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    private LocalDateTime createdAt;
}