package com.example.internship.mapper;

import com.example.internship.dto.internship.*;
import com.example.internship.model.Internship;
import com.example.internship.model.Company;

import java.util.stream.*;

public class InternshipMapper {

    public static InternshipResponseDTO toResponseDTO(Internship internship) {
        if (internship == null) return null;
        return InternshipResponseDTO.builder()
                .id(internship.getId())
                .companyId(internship.getCompany() != null ? internship.getCompany().getId() : null)
                .companyName(internship.getCompany() != null ? internship.getCompany().getName() : null)
                .title(internship.getTitle())
                .description(internship.getDescription())
                .period(internship.getPeriod())
                .conditions(internship.getConditions())
                .capacity(internship.getCapacity())
                .createdAt(internship.getCreatedAt())
                .technologies(
                    internship.getTechnologies() == null
                            ? null 
                            : internship.getTechnologies()
                            .stream()
                            .map(t -> t.getName())
                            .collect(Collectors.toList())   
                )
                .build();
    }

    public static Internship toEntity(InternshipRequestDTO dto, Company company) {
        if (dto == null) return null;
        return Internship.builder()
                .company(company)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .period(dto.getPeriod())
                .conditions(dto.getConditions())
                .capacity(dto.getCapacity())
                .build();
    }

    public static void updateEntity(Internship internship, InternshipRequestDTO dto) {
        if (internship == null || dto == null) return;
        if (dto.getTitle() != null) internship.setTitle(dto.getTitle());
        if (dto.getDescription() != null) internship.setDescription(dto.getDescription());
        if (dto.getPeriod() != null) internship.setPeriod(dto.getPeriod());
        if (dto.getConditions() != null) internship.setConditions(dto.getConditions());
        if (dto.getCapacity() != null) internship.setCapacity(dto.getCapacity());
    }
}
