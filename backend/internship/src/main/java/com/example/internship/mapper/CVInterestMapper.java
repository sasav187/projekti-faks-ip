package com.example.internship.mapper;

import com.example.internship.dto.cvinterest.*;
import com.example.internship.model.CVInterest;

public class CVInterestMapper {

    public static CVInterestResponseDTO toResponseDTO(CVInterest cvInterest) {
        if (cvInterest == null) {
            return null;
        }
        return CVInterestResponseDTO.builder()
                .id(cvInterest.getId())
                .cvId(cvInterest.getCv() != null ? cvInterest.getCv().getId() : null)
                .name(cvInterest.getName())
                .build();
    }

    public static CVInterest toEntity(CVInterestRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return CVInterest.builder()
                .name(dto.getName())
                .build();
    }

    public static void updateEntity(CVInterest cvInterest, CVInterestRequestDTO dto) {
        if (cvInterest == null || dto == null) {
            return;
        }
        if (dto.getName() != null) {
            cvInterest.setName(dto.getName());
        }
    }
}
