package com.example.internship.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "faculty_admin")
@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class FacultyAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "faculty_name")
    private String facultyName;
}