package com.example.internship.dto.internship;

import lombok.*;
import java.time.LocalDateTime;

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
}
