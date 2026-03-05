package com.example.internship.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "additional_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdditionalInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id", nullable = false)
    private CV cv;

    @Column(columnDefinition = "TEXT")
    private String content;
}