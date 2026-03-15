package com.example.internship.dto.internship;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipRequestDTO {

    private Long companyId;

    private String title;

    private String description;

    private String period;

    private String conditions;

    private Integer capacity;

    private List<String> technologies;
}
