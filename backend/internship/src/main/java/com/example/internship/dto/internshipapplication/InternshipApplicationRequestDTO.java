package com.example.internship.dto.internshipapplication;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternshipApplicationRequestDTO {

    private Long studentId;

    private Long internshipId;

    private String applicationMessage;
}