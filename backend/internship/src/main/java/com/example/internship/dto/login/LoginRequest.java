package com.example.internship.dto.login;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

    private String username;
    
    private String password;
}
