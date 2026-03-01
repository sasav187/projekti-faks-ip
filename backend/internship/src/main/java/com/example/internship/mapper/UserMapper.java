package com.example.internship.mapper;

import com.example.internship.dto.user.*;
import com.example.internship.model.User;

import java.time.LocalDateTime;

public class UserMapper {

    public static UserResponseDTO toResponseDTO(User user) {
        if (user == null) return null;

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

    public static User toEntity(UserRequestDTO dto) {
        if (dto == null) return null;

        return User.builder()
                .username(dto.getUsername())
                .role(dto.getRole())
                .active(dto.getActive() != null ? dto.getActive() : true)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static void updateEntity(User user, UserRequestDTO dto) {
        if (dto == null || user == null) return;

        if (dto.getUsername() != null) {
            user.setUsername(dto.getUsername());
        }

        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }

        if (dto.getActive() != null) {
            user.setActive(dto.getActive());
        }
    }
}