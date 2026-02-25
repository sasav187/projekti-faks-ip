package com.example.internship.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {

    private String username;
    private String password;
    private String role;
    private Boolean active;
}
