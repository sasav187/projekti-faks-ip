package com.example.internship.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cv_interest")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CVInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id", nullable = false)
    private CV cv;

    private String name;
}