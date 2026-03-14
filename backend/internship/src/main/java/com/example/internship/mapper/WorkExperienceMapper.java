package com.example.internship.mapper;

import com.example.internship.dto.workexperience.*;
import com.example.internship.model.WorkExperience;

public class WorkExperienceMapper {

    public static WorkExperienceResponseDTO toResponseDTO(WorkExperience workExperience) {
        if (workExperience == null) {
            return null;
        }

        return WorkExperienceResponseDTO.builder()
                .id(workExperience.getId())
                .cvId(workExperience.getCv() != null ? workExperience.getCv().getId() : null)
                .companyName(workExperience.getCompanyName())
                .position(workExperience.getPosition())
                .description(workExperience.getDescription())
                .startDate(workExperience.getStartDate())
                .endDate(workExperience.getEndDate())
                .build();
    }

    public static WorkExperience toEntity(WorkExperienceRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        return WorkExperience.builder()
                .companyName(dto.getCompanyName())
                .position(dto.getPosition())
                .description(dto.getDescription())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
    }

    public static void updateEntity(WorkExperience entity, WorkExperienceRequestDTO dto) {

        if (entity == null || dto == null) {
            return;
        }

        if (dto.getCompanyName() != null) {
            entity.setCompanyName(dto.getCompanyName());
        }

        if (dto.getPosition() != null) {
            entity.setPosition(dto.getPosition());
        }

        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }

        if (dto.getStartDate() != null) {
            entity.setStartDate(dto.getStartDate());
        }

        if (dto.getEndDate() != null) {
            entity.setEndDate(dto.getEndDate());
        }
    }
}