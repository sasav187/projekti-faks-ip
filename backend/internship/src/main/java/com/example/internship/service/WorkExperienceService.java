package com.example.internship.service;

import com.example.internship.dto.workexperience.*;
import com.example.internship.mapper.WorkExperienceMapper;
import com.example.internship.model.WorkExperience;
import com.example.internship.repository.WorkExperienceRepository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class WorkExperienceService {

    private final WorkExperienceRepository workExperienceRepository;

    public WorkExperienceService(WorkExperienceRepository workExperienceRepository) {
        this.workExperienceRepository = workExperienceRepository;
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

        WorkExperience entity = WorkExperienceMapper.toEntity(dto);

        return WorkExperienceMapper
                .toResponseDTO(workExperienceRepository.save(entity));
    }

    public WorkExperienceResponseDTO update(Long id, WorkExperienceRequestDTO dto) {

        WorkExperience existing = workExperienceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("WorkExperience not found with id: " + id));

        WorkExperienceMapper.updateEntity(existing, dto);

        return WorkExperienceMapper
                .toResponseDTO(workExperienceRepository.save(existing));
    }

    public void deleteById(Long id) {
        workExperienceRepository.deleteById(id);
    }
}