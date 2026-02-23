package com.example.internship.model;

import jakarta.persistence.*;
import lombok.*;

import com.example.internship.model.enums.SkillLevel;

@Entity
@Table(name = "student_skill")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentSkill {

    @EmbeddedId
    private StudentSkillId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    private Student student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("skillId")
    private Skill skill;

    @Enumerated(EnumType.STRING)
    private SkillLevel skillLevel;
}