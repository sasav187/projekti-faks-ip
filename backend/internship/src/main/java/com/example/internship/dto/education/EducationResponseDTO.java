package com.example.internship.dto.education;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationResponseDTO {

    private Long id;

    private Long cvId;

    private String institution;

    private String degree;

    private String fieldOfStudy;

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;
}