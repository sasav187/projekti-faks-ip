package com.example.internship.model;

import jakarta.persistence.*;
import lombok.*;

import com.example.internship.model.enums.LanguageLevel;

@Entity
@Table(name = "language")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id", nullable = false)
    private CV cv;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "listening_level")
    private LanguageLevel listeningLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "reading_level")
    private LanguageLevel readingLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "writing_level")
    private LanguageLevel writingLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "spoken_level")
    private LanguageLevel spokenLevel;
}