package com.example.internship.mapper;

import com.example.internship.dto.user.UserRequestDTO;
import com.example.internship.dto.user.UserResponseDTO;
import com.example.internship.model.User;
import com.example.internship.model.enums.Role;

import java.time.LocalDateTime;

public class UserMapper {
    
    public static UserResponseDTO toResponseDTO(User user) {
        if (user == null) {
            return null;
        }
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole().name())
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .lastLogin(user.getLastLogin())
                .changedPasswordAt(user.getChangedPasswordAt())
                .build();
    }

    public static User toEntity(com.example.internship.dto.user.UserRequestDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        return User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .role(Role.valueOf(userDTO.getRole()))
                .active(userDTO.getActive() != null ? userDTO.getActive() : true)
                .lastLogin(LocalDateTime.now())
                .build();
    }

    public static void updateEntity(User user, UserRequestDTO userDTO) {
        if (userDTO == null || user == null) {
            return;
        }
        if (userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }
        if (userDTO.getPassword() != null) {
            user.setPassword(userDTO.getPassword());
        }
        if (userDTO.getRole() != null) {
            user.setRole(Role.valueOf(userDTO.getRole()));
        }
        if (userDTO.getActive() != null) {
            user.setActive(userDTO.getActive());
        }
    }
}
