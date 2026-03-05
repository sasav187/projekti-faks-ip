package com.example.internship.mapper;

import com.example.internship.model.AdditionalInfo;
import com.example.internship.model.CV;
import com.example.internship.dto.additionalinfo.*;

public class AdditionalInfoMapper {

    public static AdditionalInfoResponseDTO toResponseDTO(AdditionalInfo info) {
        if (info == null) {
            return null;
        }
            return AdditionalInfoResponseDTO.builder()
                .id(info.getId())
                .cvId(info.getCv() != null ? info.getCv().getId() : null)
                .content(info.getContent())
                .build();
    }

    public static AdditionalInfo toEntity(AdditionalInfoRequestDTO dto, CV cv) {
        if (dto == null) {
            return null;
        }
        return AdditionalInfo.builder()
                .cv(cv)
                .content(dto.getContent())
                .build();
    }

    public static void updateEntity(AdditionalInfo entity, AdditionalInfoRequestDTO dto) {
        if (entity == null || dto == null) {
            return;
        }
        if (dto.getContent() != null) {
            entity.setContent(dto.getContent());
        }
    }
}