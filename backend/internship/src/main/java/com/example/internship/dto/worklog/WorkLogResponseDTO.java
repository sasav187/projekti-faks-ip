package com.example.internship.dto.worklog;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkLogResponseDTO {
    
    private Long id;

    private Long applicationId;

    private Integer weekNumber;

    private String description;
    
    private LocalDateTime createdAt;
}
