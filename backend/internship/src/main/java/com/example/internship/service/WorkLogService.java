package com.example.internship.service;

import com.example.internship.dto.worklog.WorkLogRequestDTO;
import com.example.internship.dto.worklog.WorkLogResponseDTO;
import com.example.internship.mapper.WorkLogMapper;
import com.example.internship.model.WorkLog;
import com.example.internship.model.InternshipApplication;
import com.example.internship.repository.WorkLogRepository;
import com.example.internship.repository.InternshipApplicationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class WorkLogService {

    private final WorkLogRepository workLogRepository;
    private final InternshipApplicationRepository applicationRepository;

    @Autowired
    public WorkLogService(WorkLogRepository workLogRepository,
                          InternshipApplicationRepository applicationRepository) {
        this.workLogRepository = workLogRepository;
        this.applicationRepository = applicationRepository;
    }

    public Page<WorkLogResponseDTO> getAllWorkLogs(Pageable pageable) {
        return workLogRepository.findAll(pageable)
                .map(WorkLogMapper::toResponseDTO);
    }

    public Page<WorkLogResponseDTO> searchByDescription(String description, Pageable pageable) {
        return workLogRepository
                .findByDescriptionContainingIgnoreCase(description, pageable)
                .map(WorkLogMapper::toResponseDTO);
    }

    public WorkLogResponseDTO getById(Long id) {
        WorkLog workLog = workLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkLog not found with id: " + id));

        return WorkLogMapper.toResponseDTO(workLog);
    }

    public WorkLogResponseDTO create(WorkLogRequestDTO dto) {

        InternshipApplication application = applicationRepository.findById(dto.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Application not found"));

        WorkLog workLog = WorkLogMapper.toEntity(dto, application);

        WorkLog saved = workLogRepository.save(workLog);

        return WorkLogMapper.toResponseDTO(saved);
    }

    public WorkLogResponseDTO update(Long id, WorkLogRequestDTO dto) {

        WorkLog existing = workLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkLog not found"));

        InternshipApplication application = applicationRepository.findById(dto.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Application not found"));

        existing.setApplication(application);
        existing.setWeekNumber(dto.getWeekNumber());
        existing.setDescription(dto.getDescription());

        WorkLog updated = workLogRepository.save(existing);

        return WorkLogMapper.toResponseDTO(updated);
    }

    public void deleteById(Long id) {
        workLogRepository.deleteById(id);
    }
}