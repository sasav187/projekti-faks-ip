package com.example.internship.dto.internshipapplication;

import lombok.*;
import java.time.LocalDateTime;
import com.example.internship.model.enums.ApplicationStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipApplicationResponseDTO {
    private Long id;
    private Long studentId;
    private Long internshipId;
    private String applicationMessage;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;
}
