package com.example.internship.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;
import java.util.*;

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

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "internship", cascade = CascadeType.ALL)
    private List<InternshipApplication> applications;
}