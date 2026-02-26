package com.example.internship.mapper;

import com.example.internship.dto.technology.*;
import com.example.internship.model.Technology;

public class TechnologyMapper {

    public static TechnologyResponseDTO toResponseDTO(Technology tech) {
        if (tech == null) return null;
        return TechnologyResponseDTO.builder()
                .id(tech.getId())
                .name(tech.getName())
                .build();
    }

    public static Technology toEntity(TechnologyRequestDTO dto) {
        if (dto == null) return null;
        return Technology.builder()
                .name(dto.getName())
                .build();
    }

    public static void updateEntity(Technology tech, TechnologyRequestDTO dto) {
        if (tech == null || dto == null) return;
        if (dto.getName() != null) tech.setName(dto.getName());
    }
}
