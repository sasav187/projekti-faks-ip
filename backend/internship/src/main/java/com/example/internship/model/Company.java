package com.example.internship.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "company")
@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String name;

    private String description;
    
    @Column(columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean approved;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Internship> internships;
}