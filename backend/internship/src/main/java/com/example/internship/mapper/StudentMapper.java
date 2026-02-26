package com.example.internship.mapper;

import com.example.internship.dto.student.*;
import com.example.internship.model.Student;
import com.example.internship.model.User;

public class StudentMapper {

    public static StudentResponseDTO toResponseDTO(Student student) {
        if (student == null) return null;
        return StudentResponseDTO.builder()
                .id(student.getId())
                .userId(student.getUser() != null ? student.getUser().getId() : null)
                .username(student.getUser() != null ? student.getUser().getUsername() : null)
                .indexNumber(student.getIndexNumber())
                .faculty(student.getFaculty())
                .year(student.getYear())
                .build();
    }

    public static Student toEntity(StudentRequestDTO dto, User user) {
        if (dto == null) return null;
        return Student.builder()
                .user(user)
                .indexNumber(dto.getIndexNumber())
                .faculty(dto.getFaculty())
                .year(dto.getYear())
                .build();
    }

    public static void updateEntity(Student student, StudentRequestDTO dto) {
        if (student == null || dto == null) return;
        if (dto.getIndexNumber() != null) student.setIndexNumber(dto.getIndexNumber());
        if (dto.getFaculty() != null) student.setFaculty(dto.getFaculty());
        if (dto.getYear() != null) student.setYear(dto.getYear());
    }
}