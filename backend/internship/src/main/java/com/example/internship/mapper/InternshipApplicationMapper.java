package com.example.internship.mapper;

import com.example.internship.dto.internshipapplication.*;
import com.example.internship.model.InternshipApplication;
import com.example.internship.model.Student;
import com.example.internship.model.Internship;

public class InternshipApplicationMapper {

    public static InternshipApplicationResponseDTO toResponseDTO(InternshipApplication ia) {
        if (ia == null) return null;
        return InternshipApplicationResponseDTO.builder()
                .id(ia.getId())
                .studentId(ia.getStudent() != null ? ia.getStudent().getId() : null)
                .internshipId(ia.getInternship() != null ? ia.getInternship().getId() : null)
                .applicationMessage(ia.getApplicationMessage())
                .status(ia.getStatus())
                .appliedAt(ia.getAppliedAt())
                .build();
    }

    public static InternshipApplication toEntity(InternshipApplicationRequestDTO dto,
                                                   Student student,
                                                   Internship internship) {
        if (dto == null) return null;
        return InternshipApplication.builder()
                .student(student)
                .internship(internship)
                .applicationMessage(dto.getApplicationMessage())
                .status(dto.getStatus())
                .appliedAt(dto.getAppliedAt())
                .build();
    }

    public static void updateEntity(InternshipApplication ia, InternshipApplicationRequestDTO dto) {
        if (ia == null || dto == null) return;
        if (dto.getApplicationMessage() != null) ia.setApplicationMessage(dto.getApplicationMessage());
        if (dto.getStatus() != null) ia.setStatus(dto.getStatus());
        if (dto.getAppliedAt() != null) ia.setAppliedAt(dto.getAppliedAt());
    }
}
