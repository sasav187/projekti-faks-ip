package com.example.internship.mapper;

import com.example.internship.dto.cvskill.*;
import com.example.internship.model.CVSkill;

public class CVSkillMapper {

    public static CVSkillResponseDTO toResponseDTO(CVSkill cvSkill) {
        if (cvSkill == null) return null;
        return CVSkillResponseDTO.builder()
                .id(cvSkill.getId())
                .name(cvSkill.getName())
                .build();
    }

    public static CVSkill toEntity(CVSkillRequestDTO dto) {
        if (dto == null) return null;
        return CVSkill.builder()
                .name(dto.getName())
                .build();
    }

    public static void updateEntity(CVSkill cvSkill, CVSkillRequestDTO dto) {
        if (cvSkill == null || dto == null) return;
        if (dto.getName() != null) cvSkill.setName(dto.getName());
    }
}
