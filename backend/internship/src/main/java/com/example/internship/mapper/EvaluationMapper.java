package com.example.internship.mapper;

import com.example.internship.dto.evaluation.*;
import com.example.internship.model.Evaluation;
import com.example.internship.model.Student;
import com.example.internship.model.Internship;

public class EvaluationMapper {

    public static EvaluationResponseDTO toResponseDTO(Evaluation eval) {
        if (eval == null) return null;
        return EvaluationResponseDTO.builder()
                .id(eval.getId())
                .studentId(eval.getStudent() != null ? eval.getStudent().getId() : null)
                .internshipId(eval.getInternship() != null ? eval.getInternship().getId() : null)
                .evaluatorRole(eval.getEvaluatorRole())
                .grade(eval.getGrade())
                .comment(eval.getComment())
                .evaluationDate(eval.getEvaluationDate())
                .build();
    }

    public static Evaluation toEntity(EvaluationRequestDTO dto, Student student, Internship internship) {
        if (dto == null) return null;
        return Evaluation.builder()
                .student(student)
                .internship(internship)
                .evaluatorRole(dto.getEvaluatorRole())
                .grade(dto.getGrade())
                .comment(dto.getComment())
                .evaluationDate(dto.getEvaluationDate())
                .build();
    }

    public static void updateEntity(Evaluation eval, EvaluationRequestDTO dto) {
        if (eval == null || dto == null) return;
        if (dto.getEvaluatorRole() != null) {
            eval.setEvaluatorRole(dto.getEvaluatorRole());
        }
        if (dto.getGrade() != null) {
            eval.setGrade(dto.getGrade());
        }
        if (dto.getComment() != null) {
            eval.setComment(dto.getComment());
        }
        if (dto.getEvaluationDate() != null) {
            eval.setEvaluationDate(dto.getEvaluationDate());
        }
    }
}
