package com.example.internship.dto.language;

import lombok.*;

import com.example.internship.model.enums.LanguageLevel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanguageResponseDTO {
    
    private Long id;

    private Long cvId;

    private String name;

    private LanguageLevel listeningLevel;
    
    private LanguageLevel readingLevel;

    private LanguageLevel writingLevel;
    
    private LanguageLevel spokenLevel;
}
