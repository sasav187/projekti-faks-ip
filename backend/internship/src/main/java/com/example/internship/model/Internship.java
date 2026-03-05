package com.example.internship.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "internship")
@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class Internship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    private String title;

    private String description;

    private String period;

    private String conditions;
    
    private Integer capacity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "internship", cascade = CascadeType.ALL)
    private List<InternshipApplication> applications;
}