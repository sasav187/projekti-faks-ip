package com.example.internship.dto.student;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentRequestDTO {
    private Long userId;
    private String indexNumber;
    private String faculty;
    private Integer year;
}