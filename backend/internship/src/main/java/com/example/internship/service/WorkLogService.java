package com.example.internship.service;

import com.example.internship.model.WorkLog;
import com.example.internship.repository.WorkLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class WorkLogService {
    
    private final WorkLogRepository workLogRepository;

    @Autowired
    public WorkLogService(WorkLogRepository workLogRepository) {
        this.workLogRepository = workLogRepository;
    }

    public Page<WorkLog> getAllWorkLogs(Pageable pageable) {
        return workLogRepository.findAll(pageable);
    }

    public Page<WorkLog> searchByDescription(String description, Pageable pageable) {
        return workLogRepository.findByDescriptionContainingIgnoreCase(description, pageable);
    }

    public WorkLog getById(Long id) {
        return workLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkLog not found with id: " + id));
    }

    public WorkLog save(WorkLog workLog) {
        return workLogRepository.save(workLog);
    }

    public void deleteById(Long id) {
        workLogRepository.deleteById(id);
    }
}
