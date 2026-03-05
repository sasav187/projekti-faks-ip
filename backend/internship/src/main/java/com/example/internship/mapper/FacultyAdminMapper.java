package com.example.internship.mapper;

import com.example.internship.dto.facultyadmin.*;
import com.example.internship.model.FacultyAdmin;
import com.example.internship.model.User;

public class FacultyAdminMapper {

    public static FacultyAdminResponseDTO toResponseDTO(FacultyAdmin fa) {
        if (fa == null) return null;
        return FacultyAdminResponseDTO.builder()
                .id(fa.getId())
                .userId(fa.getUser() != null ? fa.getUser().getId() : null)
                .facultyName(fa.getFacultyName())
                .build();
    }

    public static FacultyAdmin toEntity(FacultyAdminRequestDTO dto, User user) {
        if (dto == null) return null;
        return FacultyAdmin.builder()
                .user(user)
                .facultyName(dto.getFacultyName())
                .build();
    }

    public static void updateEntity(FacultyAdmin fa, FacultyAdminRequestDTO dto) {
        if (fa == null || dto == null) return;
        if (dto.getFacultyName() != null) {
            fa.setFacultyName(dto.getFacultyName());
        }
    }
}
