package com.example.internship.dto.user;

import lombok.*;

import com.example.internship.model.enums.Role;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {

    private String username;

    private String password;

    private Role role;
    
    private Boolean active;
}
