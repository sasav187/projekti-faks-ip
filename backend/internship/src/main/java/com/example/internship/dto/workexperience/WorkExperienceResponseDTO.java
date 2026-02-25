package com.example.internship.dto.workexperience;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkExperienceResponseDTO {
 
    private Long id;
    private String companyName;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long studentId;
}
