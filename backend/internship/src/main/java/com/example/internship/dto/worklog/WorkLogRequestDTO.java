package com.example.internship.dto.worklog;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkLogRequestDTO {

    private Long applicationId;

    private Integer weekNumber;
    
    private String description;
}
