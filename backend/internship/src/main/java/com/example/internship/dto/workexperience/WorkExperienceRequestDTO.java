package com.example.internship.dto.workexperience;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkExperienceRequestDTO {

    private Long cvId;

    private String companyName;

    private String position;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;
}
