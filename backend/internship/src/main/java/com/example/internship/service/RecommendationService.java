package com.example.internship.service;

import com.example.internship.dto.recommendation.*;
import com.example.internship.mapper.RecommendationMapper;
import com.example.internship.model.Recommendation;
import com.example.internship.model.Student;
import com.example.internship.model.Internship;
import com.example.internship.repository.RecommendationRepository;
import com.example.internship.repository.StudentRepository;
import com.example.internship.repository.InternshipRepository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService {
    
    private final RecommendationRepository recommendationRepository;
    private final StudentRepository studentRepository;
    private final InternshipRepository internshipRepository;

    public RecommendationService(RecommendationRepository recommendationRepository,
                                 StudentRepository studentRepository,
                                 InternshipRepository internshipRepository) {
        this.recommendationRepository = recommendationRepository;
        this.studentRepository = studentRepository;
        this.internshipRepository = internshipRepository;
    }

    public Page<RecommendationResponseDTO> getAllRecommendations(Pageable pageable) {
        return recommendationRepository.findAll(pageable)
                .map(RecommendationMapper::toResponseDTO);
    }

    public Page<RecommendationResponseDTO> searchByExplanation(String explanation, Pageable pageable) {
        return recommendationRepository.findByExplanationContainingIgnoreCase(explanation, pageable)
                .map(RecommendationMapper::toResponseDTO);
    }

    public RecommendationResponseDTO getById(Long id) {
        Recommendation rec = recommendationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recommendation not found with id: " + id));
        return RecommendationMapper.toResponseDTO(rec);
    }

    public RecommendationResponseDTO create(RecommendationRequestDTO dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + dto.getStudentId()));
        Internship internship = internshipRepository.findById(dto.getInternshipId())
                .orElseThrow(() -> new RuntimeException("Internship not found with id: " + dto.getInternshipId()));
        Recommendation entity = RecommendationMapper.toEntity(dto, student, internship);
        return RecommendationMapper.toResponseDTO(recommendationRepository.save(entity));
    }

    public RecommendationResponseDTO update(Long id, RecommendationRequestDTO dto) {
        Recommendation existing = recommendationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recommendation not found with id: " + id));
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + dto.getStudentId()));
        Internship internship = internshipRepository.findById(dto.getInternshipId())
                .orElseThrow(() -> new RuntimeException("Internship not found with id: " + dto.getInternshipId()));

        RecommendationMapper.updateEntity(existing, dto);
        existing.setStudent(student);
        existing.setInternship(internship);

        return RecommendationMapper.toResponseDTO(recommendationRepository.save(existing));
    }

    public void deleteById(Long id) {
        recommendationRepository.deleteById(id);
    }
}
