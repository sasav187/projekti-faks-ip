package com.example.internship.mapper;

import com.example.internship.dto.education.*;
import com.example.internship.model.Education;

public class EducationMapper {

    public static EducationResponseDTO toResponseDTO(Education education) {
        if (education == null) return null;
        return EducationResponseDTO.builder()
                .id(education.getId())
                .cvId(education.getCv().getId())
                .institution(education.getInstitution())
                .degree(education.getDegree())
                .fieldOfStudy(education.getFieldOfStudy())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .description(education.getDescription())
                .build();
    }

    public static Education toEntity(EducationRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return Education.builder()
                .institution(dto.getInstitution())
                .degree(dto.getDegree())
                .fieldOfStudy(dto.getFieldOfStudy())
                .startDate(dto.getStartDate())  
                .endDate(dto.getEndDate())
                .description(dto.getDescription())
                .build();
    }

    public static void updateEntity(Education education, EducationRequestDTO dto) {
        if (education == null || dto == null) {
            return;
        }
        if (dto.getInstitution() != null) {
            education.setInstitution(dto.getInstitution());
        }
        if (dto.getDegree() != null) {
            education.setDegree(dto.getDegree());
        }
        if (dto.getFieldOfStudy() != null) {
            education.setFieldOfStudy(dto.getFieldOfStudy());
        }
        if (dto.getStartDate() != null) {
            education.setStartDate(dto.getStartDate());
        }
        if (dto.getEndDate() != null) {
            education.setEndDate(dto.getEndDate());
        }
        if (dto.getDescription() != null) {
            education.setDescription(dto.getDescription());
        }
    }
}
