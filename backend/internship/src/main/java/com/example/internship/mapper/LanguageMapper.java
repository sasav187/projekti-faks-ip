package com.example.internship.mapper;

import com.example.internship.dto.language.*;
import com.example.internship.model.Language;

public class LanguageMapper {

    public static LanguageResponseDTO toResponseDTO(Language language) {
        if (language == null) return null;
        return LanguageResponseDTO.builder()
                .id(language.getId())
                .cvId(language.getCv().getId())
                .name(language.getName())
                .listeningLevel(language.getListeningLevel())
                .readingLevel(language.getReadingLevel())
                .writingLevel(language.getWritingLevel())
                .spokenLevel(language.getSpokenLevel())
                .build();
    }

    public static Language toEntity(LanguageRequestDTO dto) {
        if (dto == null) return null;
        return Language.builder()
                .name(dto.getName())
                .listeningLevel(dto.getListeningLevel())
                .readingLevel(dto.getReadingLevel())
                .writingLevel(dto.getWritingLevel())
                .spokenLevel(dto.getSpokenLevel())
                .build();
    }

    public static void updateEntity(Language entity, LanguageRequestDTO dto) {
        if (entity == null || dto == null) return;
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getListeningLevel() != null) entity.setListeningLevel(dto.getListeningLevel());
        if (dto.getReadingLevel() != null) entity.setReadingLevel(dto.getReadingLevel());
        if (dto.getWritingLevel() != null) entity.setWritingLevel(dto.getWritingLevel());
        if (dto.getSpokenLevel() != null) entity.setSpokenLevel(dto.getSpokenLevel());
    }
}