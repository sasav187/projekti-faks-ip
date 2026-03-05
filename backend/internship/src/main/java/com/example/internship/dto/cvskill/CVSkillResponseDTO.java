package com.example.internship.dto.cvskill;

import lombok.*;

import com.example.internship.model.enums.SkillLevel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CVSkillResponseDTO {

    private Long id;

    private Long cvId;

    private String name;

    private SkillLevel level;
}
