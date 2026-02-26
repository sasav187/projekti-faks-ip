package com.example.internship.dto.recommendation;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationRequestDTO {
    private Long studentId;
    private Long internshipId;
    private Double score;
    private String explanation;
}
