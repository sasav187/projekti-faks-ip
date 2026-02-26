package com.example.internship.dto.facultyadmin;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacultyAdminResponseDTO {
    private Long id;
    private Long userId;
    private String username;
    private String facultyName;
}
