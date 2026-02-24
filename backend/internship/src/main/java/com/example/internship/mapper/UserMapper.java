package com.example.internship.mapper;

import com.example.internship.dto.UserDTO;
import com.example.internship.model.User;

public class UserMapper {
    
    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole().name())
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .lastLogin(user.getLastLogin())
                .build();
    }

    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .active(userDTO.getActive())
                .lastLogin(userDTO.getLastLogin())
                .build();
    }
}
