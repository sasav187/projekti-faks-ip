package com.example.internship.service;

import com.example.internship.dto.workexperience.*;
import com.example.internship.mapper.WorkExperienceMapper;
import com.example.internship.model.WorkExperience;
import com.example.internship.model.Student;
import com.example.internship.repository.WorkExperienceRepository;
import com.example.internship.repository.StudentRepository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class WorkExperienceService {

    private final WorkExperienceRepository workExperienceRepository;
    private final StudentRepository studentRepository;

    public WorkExperienceService(WorkExperienceRepository workExperienceRepository,
                                 StudentRepository studentRepository) {
        this.workExperienceRepository = workExperienceRepository;
        this.studentRepository = studentRepository;
    }

    public Page<WorkExperienceResponseDTO> getAllWorkExperiences(Pageable pageable) {
        return workExperienceRepository.findAll(pageable)
                .map(WorkExperienceMapper::toResponseDTO);
    }

    public Page<WorkExperienceResponseDTO> searchByCompanyName(String companyName,
                                                               Pageable pageable) {
        return workExperienceRepository
                .findByCompanyNameContainingIgnoreCase(companyName, pageable)
                .map(WorkExperienceMapper::toResponseDTO);
    }

    public WorkExperienceResponseDTO getById(Long id) {
        WorkExperience entity = workExperienceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("WorkExperience not found with id: " + id));

        return WorkExperienceMapper.toResponseDTO(entity);
    }

    public WorkExperienceResponseDTO create(WorkExperienceRequestDTO dto) {

        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() ->
                        new RuntimeException("Student not found with id: " + dto.getStudentId()));

        WorkExperience entity = WorkExperienceMapper.toEntity(dto, student);

        return WorkExperienceMapper
                .toResponseDTO(workExperienceRepository.save(entity));
    }

    public WorkExperienceResponseDTO update(Long id, WorkExperienceRequestDTO dto) {

        WorkExperience existing = workExperienceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("WorkExperience not found with id: " + id));

        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() ->
                        new RuntimeException("Student not found with id: " + dto.getStudentId()));

        existing.setCompanyName(dto.getCompanyName());
        existing.setDescription(dto.getDescription());
        existing.setStartDate(dto.getStartDate());
        existing.setEndDate(dto.getEndDate());
        existing.setStudent(student);

        return WorkExperienceMapper
                .toResponseDTO(workExperienceRepository.save(existing));
    }

    public void deleteById(Long id) {
        workExperienceRepository.deleteById(id);
    }
}