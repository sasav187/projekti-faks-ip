package com.example.internship.service;

import com.example.internship.dto.cv.*;
import com.example.internship.mapper.CVMapper;
import com.example.internship.model.CV;
import com.example.internship.model.Student;
import com.example.internship.repository.CVRepository;
import com.example.internship.repository.StudentRepository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class CVService {

    private final CVRepository cvRepository;
    private final StudentRepository studentRepository;
    private final CVMapper cvMapper;

    public CVService(CVRepository cvRepository,
                     StudentRepository studentRepository,
                     CVMapper cvMapper) {
        this.cvRepository = cvRepository;
        this.studentRepository = studentRepository;
        this.cvMapper = cvMapper;
    }

    public Page<CVResponseDTO> getAllCVs(Pageable pageable) {
        return cvRepository.findAll(pageable)
                .map(cvMapper::toDTO);
    }

    public Page<CVResponseDTO> searchBySummary(String summary, Pageable pageable) {
        return cvRepository.findBySummaryContainingIgnoreCase(summary, pageable)
                .map(cvMapper::toDTO);
    }

    public CVResponseDTO getById(Long id) {
        CV cv = cvRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CV not found with id: " + id));

        return cvMapper.toDTO(cv);
    }

    public CVResponseDTO create(CVRequestDTO dto) {

        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() ->
                        new RuntimeException("Student not found with id: " + dto.getStudentId()));

        CV entity = cvMapper.toEntity(dto, student);

        return cvMapper.toDTO(
                cvRepository.save(entity)
        );
    }

    public CVResponseDTO update(Long id, CVRequestDTO dto) {

        CV existing = cvRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("CV not found with id: " + id));

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());
        existing.setAddress(dto.getAddress());
        existing.setNationality(dto.getNationality());
        existing.setDateOfBirth(dto.getDateOfBirth());
        existing.setSummary(dto.getSummary());
        existing.setImagePath(dto.getImagePath());

        return cvMapper.toDTO(
                cvRepository.save(existing)
        );
    }

    public void deleteById(Long id) {
        cvRepository.deleteById(id);
    }
}