package com.example.internship.mapper;

import com.example.internship.dto.recommendation.*;
import com.example.internship.model.Recommendation;
import com.example.internship.model.Student;
import com.example.internship.model.Internship;

public class RecommendationMapper {

    public static RecommendationResponseDTO toResponseDTO(Recommendation rec) {
        if (rec == null) return null;
        return RecommendationResponseDTO.builder()
                .id(rec.getId())
                .studentId(rec.getStudent() != null ? rec.getStudent().getId() : null)
                .internshipId(rec.getInternship() != null ? rec.getInternship().getId() : null)
                .score(rec.getScore())
                .explanation(rec.getExplanation())
                .createdAt(rec.getCreatedAt())
                .build();
    }

    public static Recommendation toEntity(RecommendationRequestDTO dto,
                                           Student student,
                                           Internship internship) {
        if (dto == null) return null;
        return Recommendation.builder()
                .student(student)
                .internship(internship)
                .score(dto.getScore())
                .explanation(dto.getExplanation())
                .build();
    }

    public static void updateEntity(Recommendation rec, RecommendationRequestDTO dto) {
        if (rec == null || dto == null) return;
        if (dto.getScore() != null) rec.setScore(dto.getScore());
        if (dto.getExplanation() != null) rec.setExplanation(dto.getExplanation());
    }
}
