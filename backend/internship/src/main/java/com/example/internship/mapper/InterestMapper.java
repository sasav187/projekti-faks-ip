package com.example.internship.mapper;

import com.example.internship.dto.interest.*;
import com.example.internship.model.Interest;

public class InterestMapper {

    public static InterestResponseDTO toResponseDTO(Interest interest) {
        if (interest == null) return null;
        return InterestResponseDTO.builder()
                .id(interest.getId())
                .name(interest.getName())
                .build();
    }

    public static Interest toEntity(InterestRequestDTO dto) {
        if (dto == null) return null;
        return Interest.builder()
                .name(dto.getName())
                .build();
    }

    public static void updateEntity(Interest interest, InterestRequestDTO dto) {
        if (interest == null || dto == null) return;
        if (dto.getName() != null) {
            interest.setName(dto.getName());
        }
    }
}
