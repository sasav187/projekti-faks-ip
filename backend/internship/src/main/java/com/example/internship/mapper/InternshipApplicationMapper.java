package com.example.internship.mapper;

import com.example.internship.dto.internshipapplication.*;
import com.example.internship.model.InternshipApplication;

public class InternshipApplicationMapper {

    public static InternshipApplicationResponseDTO toResponseDTO(InternshipApplication ia) {
        if (ia == null)
            return null;

        String studentName = null;
        if (ia.getStudent() != null && ia.getStudent().getUser() != null) {
            studentName = ia.getStudent().getUser().getUsername();
        }

        String internshipTitle = null;
        String companyName = null;
        if (ia.getInternship() != null) {
            internshipTitle = ia.getInternship().getTitle();
            if (ia.getInternship().getCompany() != null) {
                companyName = ia.getInternship().getCompany().getName();
            }
        }

        return InternshipApplicationResponseDTO.builder()
                .id(ia.getId())
                .studentId(ia.getStudent() != null ? ia.getStudent().getId() : null)
                .studentName(studentName)
                .internshipId(ia.getInternship() != null ? ia.getInternship().getId() : null)
                .internshipTitle(internshipTitle)
                .companyName(companyName)
                .applicationMessage(ia.getApplicationMessage())
                .status(ia.getStatus())
                .appliedAt(ia.getAppliedAt())
                .build();
    }

    public static InternshipApplication toEntity(InternshipApplicationRequestDTO dto,
            com.example.internship.model.Student student,
            com.example.internship.model.Internship internship) {
        if (dto == null)
            return null;
        return InternshipApplication.builder()
                .student(student)
                .internship(internship)
                .applicationMessage(dto.getApplicationMessage())
                .build();
    }

    public static void updateEntity(InternshipApplication ia, InternshipApplicationRequestDTO dto) {
        if (ia == null || dto == null)
            return;
        if (dto.getApplicationMessage() != null)
            ia.setApplicationMessage(dto.getApplicationMessage());
    }
}