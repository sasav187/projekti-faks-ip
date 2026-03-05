package com.example.internship.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student")
@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "index_number", unique = true, nullable = false)
    private String indexNumber;

    private String faculty;
    
    private Integer year;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private CV cv;
}