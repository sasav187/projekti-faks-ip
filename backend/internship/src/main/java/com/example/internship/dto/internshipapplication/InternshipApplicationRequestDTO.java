package com.example.internship.dto.internshipapplication;

import lombok.*;
import com.example.internship.model.enums.ApplicationStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipApplicationRequestDTO {
    private Long studentId;
    private Long internshipId;
    private String applicationMessage;
    private ApplicationStatus status;
    // timestamp when the student applied
    private java.time.LocalDateTime appliedAt;

    // explicit getters and setters (Lombok may not run)
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getInternshipId() { return internshipId; }
    public void setInternshipId(Long internshipId) { this.internshipId = internshipId; }

    public String getApplicationMessage() { return applicationMessage; }
    public void setApplicationMessage(String applicationMessage) { this.applicationMessage = applicationMessage; }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }

    public java.time.LocalDateTime getAppliedAt() { return appliedAt; }
    public void setAppliedAt(java.time.LocalDateTime appliedAt) { this.appliedAt = appliedAt; }
}
