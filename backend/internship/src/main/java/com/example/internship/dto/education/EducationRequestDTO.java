package com.example.internship.dto.education;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationRequestDTO {
    private Long studentId;
    private String institution;
    private String degree;
    private Integer startYear;
    private Integer endYear;
}
