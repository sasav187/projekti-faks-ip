package com.example.internship.dto.internship;

import lombok.*;
import java.time.LocalDateTime;

import java.util.List;

import com.example.internship.model.Technology;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipResponseDTO {

    private Long id;

    private Long companyId;

    private String companyName;

    private String title;

    private String description;

    private String period;

    private String conditions;

    private Integer capacity;
    
    private LocalDateTime createdAt;

    private List<String> technologies;
}
