package com.example.internship.dto.evaluation;

import lombok.*;
import java.time.LocalDateTime;
import com.example.internship.model.enums.EvaluatorRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationResponseDTO {
    private Long id;
    private Long studentId;
    private Long internshipId;
    private EvaluatorRole evaluatorRole;
    private Integer grade;
    private String comment;
    private LocalDateTime evaluationDate;
}
