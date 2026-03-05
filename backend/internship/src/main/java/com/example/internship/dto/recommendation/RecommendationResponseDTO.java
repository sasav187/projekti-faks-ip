package com.example.internship.dto.recommendation;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationResponseDTO {

    private Long id;

    private Long studentId;

    private Long internshipId;

    private Double score;

    private String explanation;
    
    private LocalDateTime createdAt;
}
