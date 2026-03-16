package com.example.internship.service;

import com.example.internship.dto.internshipapplication.InternshipApplicationResponseDTO;
import com.example.internship.dto.worklog.WorkLogRequestDTO;
import com.example.internship.dto.worklog.WorkLogResponseDTO;
import com.example.internship.mapper.InternshipApplicationMapper;
import com.example.internship.mapper.WorkLogMapper;
import com.example.internship.model.WorkLog;
import com.example.internship.model.InternshipApplication;
import com.example.internship.model.User;
import com.example.internship.model.Student;
import com.example.internship.repository.WorkLogRepository;
import com.example.internship.repository.InternshipApplicationRepository;
import com.example.internship.repository.UserRepository;
import com.example.internship.repository.StudentRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class WorkLogService {

    private final WorkLogRepository workLogRepository;
    private final InternshipApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public WorkLogService(
            WorkLogRepository workLogRepository,
            InternshipApplicationRepository applicationRepository,
            UserRepository userRepository,
            StudentRepository studentRepository) {
        this.workLogRepository = workLogRepository;
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
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

    public WorkLogResponseDTO create(WorkLogRequestDTO dto, String username) {

        Student student = studentRepository
                .findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        InternshipApplication application = applicationRepository
                .findById(dto.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (!application.getStudent().getId().equals(student.getId())) {
            throw new RuntimeException("You cannot write logs for this internship");
        }

        boolean weekExists = workLogRepository
                .existsByApplicationIdAndWeekNumber(
                        dto.getApplicationId(),
                        dto.getWeekNumber());

        if (weekExists) {
            throw new RuntimeException("Week already exists");
        }

        WorkLog workLog = WorkLogMapper.toEntity(dto, application);

        WorkLog saved = workLogRepository.save(workLog);

        return WorkLogMapper.toResponseDTO(saved);
    }

    // public WorkLogResponseDTO update(Long id, WorkLogRequestDTO dto) {

    // WorkLog existing = workLogRepository.findById(id)
    // .orElseThrow(() -> new RuntimeException("WorkLog not found"));

    // InternshipApplication application =
    // applicationRepository.findById(dto.getApplicationId())
    // .orElseThrow(() -> new RuntimeException("Application not found"));

    // existing.setApplication(application);
    // existing.setWeekNumber(dto.getWeekNumber());
    // existing.setDescription(dto.getDescription());

    // WorkLog updated = workLogRepository.save(existing);

    // return WorkLogMapper.toResponseDTO(updated);
    // }

    public WorkLogResponseDTO update(Long id, WorkLogRequestDTO dto, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        WorkLog existing = workLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkLog not found"));

        InternshipApplication application = applicationRepository
                .findById(dto.getApplicationId())
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (!application.getStudent().getId().equals(student.getId())) {
            throw new RuntimeException("You cannot modify this work log");
        }

        existing.setApplication(application);
        existing.setWeekNumber(dto.getWeekNumber());
        existing.setDescription(dto.getDescription());

        WorkLog updated = workLogRepository.save(existing);

        return WorkLogMapper.toResponseDTO(updated);
    }

    public void deleteById(Long id, String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Student student = studentRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        WorkLog workLog = workLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkLog not found"));

        if (!workLog.getApplication().getStudent().getId().equals(student.getId())) {
            throw new RuntimeException("You cannot delete this work log");
        }

        workLogRepository.delete(workLog);
    }

    public Page<WorkLogResponseDTO> getByApplication(Long applicationId, Pageable pageable) {

        InternshipApplication application = applicationRepository
                .findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        return workLogRepository
                .findByApplication(application, pageable)
                .map(WorkLogMapper::toResponseDTO);
    }

    public List<InternshipApplicationResponseDTO> getApplicationsByStudent(String username) {
    
        Student student = studentRepository
                .findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<InternshipApplication> applications = applicationRepository.findByStudent(student);

        return applications.stream()
                .map(InternshipApplicationMapper::toResponseDTO)
                .toList();
    }

    // public void deleteById(Long id) {
    // workLogRepository.deleteById(id);
    // }
}