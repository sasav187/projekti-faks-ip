package com.example.internship.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

import com.example.internship.model.enums.ApplicationStatus;

@Entity
@Table(name = "internship_application")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    private Internship internship;

    @Lob
    private String applicationMessage;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private LocalDateTime appliedAt;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL)
    private List<WorkLog> workLogs;
}
