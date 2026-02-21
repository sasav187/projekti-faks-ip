package com.example.internship.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cv")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    
    @Column(columnDefinition = "TEXT")
    private String summary;

    private String imagePath;
}
