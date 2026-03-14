package com.example.internship.service;

import com.example.internship.dto.education.*;
import com.example.internship.mapper.EducationMapper;
import com.example.internship.model.Education;
import com.example.internship.repository.EducationRepository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class EducationService {

    private final EducationRepository educationRepository;

    public EducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    public Page<EducationResponseDTO> getAllEducations(Pageable pageable) {
        return educationRepository.findAll(pageable)
                .map(EducationMapper::toResponseDTO);
    }

    public Page<EducationResponseDTO> searchByInstitution(String institution, Pageable pageable) {
        return educationRepository
                .findByInstitutionContainingIgnoreCase(institution, pageable)
                .map(EducationMapper::toResponseDTO);
    }

    public EducationResponseDTO getById(Long id) {
        Education entity = educationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Education not found with id: " + id));

        return EducationMapper.toResponseDTO(entity);
    }

    public EducationResponseDTO create(EducationRequestDTO dto) {

        Education entity = EducationMapper.toEntity(dto);

        return EducationMapper.toResponseDTO(
                educationRepository.save(entity)
        );
    }

    public EducationResponseDTO update(Long id, EducationRequestDTO dto) {

        Education existing = educationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Education not found with id: " + id));

        EducationMapper.updateEntity(existing, dto);

        return EducationMapper.toResponseDTO(
                educationRepository.save(existing)
        );
    }

    public void deleteById(Long id) {
        educationRepository.deleteById(id);
    }
}