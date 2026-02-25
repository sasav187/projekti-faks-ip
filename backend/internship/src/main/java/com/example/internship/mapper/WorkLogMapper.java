package com.example.internship.mapper;

import java.time.LocalDateTime;

import com.example.internship.dto.worklog.*;
import com.example.internship.model.WorkLog;
import com.example.internship.model.InternshipApplication;

public class WorkLogMapper {
    
    public static WorkLogResponseDTO toResponseDTO(WorkLog workLog) {
        if (workLog == null) {
            return null;
        }
        return WorkLogResponseDTO.builder()
                .id(workLog.getId())
                .applicationId(workLog.getApplication().getId())
                .weekNumber(workLog.getWeekNumber())
                .description(workLog.getDescription())
                .createdAt(workLog.getCreatedAt())
                .build();
    }

    public static WorkLog toEntity(WorkLogRequestDTO dto, InternshipApplication application) {
        if (dto == null) {
            return null;
        }
        return WorkLog.builder()
                .application(application)
                .weekNumber(dto.getWeekNumber())
                .description(dto.getDescription())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
