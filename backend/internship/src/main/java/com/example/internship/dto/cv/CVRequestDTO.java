package com.example.internship.dto.cv;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CVRequestDTO {

    private Long studentId;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String address;
    
    private String nationality;

    private LocalDate dateOfBirth;

    private String summary;

    private String imagePath;
}