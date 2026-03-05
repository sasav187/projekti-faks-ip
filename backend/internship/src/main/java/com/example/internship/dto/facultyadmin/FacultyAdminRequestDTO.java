package com.example.internship.dto.facultyadmin;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacultyAdminRequestDTO {

    private Long userId;
    
    private String facultyName;
}
