package com.example.internship.model;

import jakarta.persistence.*;
import lombok.*;

import com.example.internship.model.enums.SkillLevel;

@Entity
@Table(name = "cv_skill")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CVSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id", nullable = false)
    private CV cv;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private SkillLevel level; 
}