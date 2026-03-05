package com.example.internship.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.*;
import java.util.List;

@Entity
@Table(name = "cv")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false, unique = true)
    private Student student;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String phone;

    private String address;

    private String nationality;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        lastUpdated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CVSkill> skills;

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CVInterest> interests;

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> educationList;

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkExperience> workExperiences;

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Language> languages;

    @OneToMany(mappedBy = "cv", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdditionalInfo> additionalInfos;
}