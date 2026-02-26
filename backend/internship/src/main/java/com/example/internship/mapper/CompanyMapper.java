package com.example.internship.mapper;

import com.example.internship.dto.company.*;
import com.example.internship.model.Company;
import com.example.internship.model.User;

public class CompanyMapper {

    public static CompanyResponseDTO toResponseDTO(Company company) {
        if (company == null) {
            return null;
        }

        return CompanyResponseDTO.builder()
                .id(company.getId())
                .userId(company.getUser().getId())
                .username(company.getUser().getUsername())
                .name(company.getName())
                .description(company.getDescription())
                .approved(company.getApproved())
                .build();
    }

    public static Company toEntity(CompanyRequestDTO dto, User user) {
        if (dto == null || user == null) {
            return null;
        }

        return Company.builder()
                .user(user)
                .name(dto.getName())
                .description(dto.getDescription())
                .approved(dto.getApproved() != null ? dto.getApproved() : false)
                .build();
    }

    public static void updateEntity(Company company, CompanyRequestDTO dto) {
        if (company == null || dto == null) {
            return;
        }

        if (dto.getName() != null) {
            company.setName(dto.getName());
        }

        if (dto.getDescription() != null) {
            company.setDescription(dto.getDescription());
        }

        if (dto.getApproved() != null) {
            company.setApproved(dto.getApproved());
        }
    }
}
