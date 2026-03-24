package com.example.internship.dto.internshipapplication;

import com.example.internship.model.enums.ApplicationStatus;

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

    private ApplicationStatus status;
}