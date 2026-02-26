package com.example.internship.mapper;

import com.example.internship.dto.workexperience.*;
import com.example.internship.model.Student;
import com.example.internship.model.WorkExperience;

public class WorkExperienceMapper {
    
    public static WorkExperienceResponseDTO toResponseDTO(WorkExperience workExperience) {
        if (workExperience == null) {
            return null;
        }

        return WorkExperienceResponseDTO.builder()
                .id(workExperience.getId())
                .companyName(workExperience.getCompanyName())
                .description(workExperience.getDescription())
                .startDate(workExperience.getStartDate())
                .endDate(workExperience.getEndDate())
                .studentId(workExperience.getStudent() != null ? workExperience.getStudent().getId() : null)
                .build();
    }

    public static WorkExperience toEntity(WorkExperienceRequestDTO dto, Student student) {
        if (dto == null) {
            return null;
        }

        return WorkExperience.builder()
                .companyName(dto.getCompanyName())
                .description(dto.getDescription())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .student(student)
                .build();
    }
}
