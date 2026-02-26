package com.example.internship.mapper;

import com.example.internship.dto.skill.*;
import com.example.internship.model.Skill;

public class SkillMapper {

    public static SkillResponseDTO toResponseDTO(Skill skill) {
        if (skill == null) return null;
        return SkillResponseDTO.builder()
                .id(skill.getId())
                .name(skill.getName())
                .build();
    }

    public static Skill toEntity(SkillRequestDTO dto) {
        if (dto == null) return null;
        return Skill.builder()
                .name(dto.getName())
                .build();
    }

    public static void updateEntity(Skill skill, SkillRequestDTO dto) {
        if (skill == null || dto == null) return;
        if (dto.getName() != null) skill.setName(dto.getName());
    }
}
