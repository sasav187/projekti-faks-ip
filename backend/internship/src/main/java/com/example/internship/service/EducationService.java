package com.example.internship.service;

import com.example.internship.dto.education.*;
import com.example.internship.mapper.EducationMapper;
import com.example.internship.model.Education;
import com.example.internship.model.Student;
import com.example.internship.repository.EducationRepository;
import com.example.internship.repository.StudentRepository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class EducationService {

    private final EducationRepository educationRepository;
    private final StudentRepository studentRepository;

    public EducationService(EducationRepository educationRepository,
                             StudentRepository studentRepository) {
        this.educationRepository = educationRepository;
        this.studentRepository = studentRepository;
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
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + dto.getStudentId()));
        Education entity = EducationMapper.toEntity(dto, student);
        return EducationMapper.toResponseDTO(educationRepository.save(entity));
    }

    public EducationResponseDTO update(Long id, EducationRequestDTO dto) {
        Education existing = educationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Education not found with id: " + id));
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + dto.getStudentId()));

        EducationMapper.updateEntity(existing, dto);
        existing.setStudent(student);

        return EducationMapper.toResponseDTO(educationRepository.save(existing));
    }

    public void deleteById(Long id) {
        educationRepository.deleteById(id);
    }
}
