package com.example.internship.mapper;

import com.example.internship.dto.education.*;
import com.example.internship.model.Education;
import com.example.internship.model.Student;

public class EducationMapper {

    public static EducationResponseDTO toResponseDTO(Education education) {
        if (education == null) return null;
        return EducationResponseDTO.builder()
                .id(education.getId())
                .studentId(education.getStudent() != null ? education.getStudent().getId() : null)
                .institution(education.getInstitution())
                .degree(education.getDegree())
                .startYear(education.getStartYear())
                .endYear(education.getEndYear())
                .build();
    }

    public static Education toEntity(EducationRequestDTO dto, Student student) {
        if (dto == null) return null;
        return Education.builder()
                .student(student)
                .institution(dto.getInstitution())
                .degree(dto.getDegree())
                .startYear(dto.getStartYear())
                .endYear(dto.getEndYear())
                .build();
    }

    public static void updateEntity(Education education, EducationRequestDTO dto) {
        if (education == null || dto == null) return;
        if (dto.getInstitution() != null) {
            education.setInstitution(dto.getInstitution());
        }
        if (dto.getDegree() != null) {
            education.setDegree(dto.getDegree());
        }
        if (dto.getStartYear() != null) {
            education.setStartYear(dto.getStartYear());
        }
        if (dto.getEndYear() != null) {
            education.setEndYear(dto.getEndYear());
        }
    }
}
