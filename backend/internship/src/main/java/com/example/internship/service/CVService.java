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

    public CVService(CVRepository cvRepository,
                     StudentRepository studentRepository) {
        this.cvRepository = cvRepository;
        this.studentRepository = studentRepository;
    }

    public Page<CVResponseDTO> getAllCVs(Pageable pageable) {
        return cvRepository.findAll(pageable)
                .map(CVMapper::toResponseDTO);
    }

    public Page<CVResponseDTO> searchBySummary(String summary, Pageable pageable) {
        return cvRepository.findBySummaryContainingIgnoreCase(summary, pageable)
                .map(CVMapper::toResponseDTO);
    }

    public CVResponseDTO getById(Long id) {
        CV cv = cvRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CV not found with id: " + id));
        return CVMapper.toResponseDTO(cv);
    }

    public CVResponseDTO create(CVRequestDTO dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + dto.getStudentId()));
        CV entity = CVMapper.toEntity(dto, student);
        return CVMapper.toResponseDTO(cvRepository.save(entity));
    }

    public CVResponseDTO update(Long id, CVRequestDTO dto) {
        CV existing = cvRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CV not found with id: " + id));
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + dto.getStudentId()));
        // update fields via mapper
        existing.setSummary(dto.getSummary());
        existing.setImagePath(dto.getImagePath());
        existing.setStudent(student);
        return CVMapper.toResponseDTO(cvRepository.save(existing));
    }

    public void deleteById(Long id) {
        cvRepository.deleteById(id);
    }
}
