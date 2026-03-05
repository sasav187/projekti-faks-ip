package com.example.internship.dto.user;

import lombok.*;   

import java.time.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;

    private String username;

    private String role;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime lastLogin;
    
    private LocalDateTime changedPasswordAt;
}
