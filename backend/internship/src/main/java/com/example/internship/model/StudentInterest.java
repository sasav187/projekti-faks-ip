package com.example.internship.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student_interest")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentInterest {

    @EmbeddedId
    private StudentInterestId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    private Student student;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("interestId")
    private Interest interest;
}