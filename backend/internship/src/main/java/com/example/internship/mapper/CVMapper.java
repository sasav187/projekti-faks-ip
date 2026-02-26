package com.example.internship.mapper;

import com.example.internship.dto.cv.*;
import com.example.internship.model.CV;
import com.example.internship.model.Student;

public class CVMapper {
    
    public static CV toEntity(CVRequestDTO dto, Student student) {
        if (dto == null) {
            return null;
        }
        
        return CV.builder()
                .student(student)
                .summary(dto.getSummary())
                .imagePath(dto.getImagePath())
                .build();
    }

    public static CVResponseDTO toResponseDTO(CV cv) {
        if (cv == null) {
            return null;
        }
        
        // avoid Lombok builder in case annotation processing fails
        CVResponseDTO dto = new CVResponseDTO();
        dto.setId(cv.getId());
        dto.setStudentId(cv.getStudent().getId());
        dto.setStudentName(cv.getStudent().getUser().getUsername());
        dto.setSummary(cv.getSummary());
        dto.setImagePath(cv.getImagePath());
        dto.setCreatedAt(cv.getCreatedAt());
        return dto;
    }
}
