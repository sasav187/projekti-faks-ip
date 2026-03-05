package com.example.internship.dto.student;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponseDTO {

    private Long id;

    private Long userId;

    private String username;

    private String indexNumber;

    private String faculty;
    
    private Integer year;
}