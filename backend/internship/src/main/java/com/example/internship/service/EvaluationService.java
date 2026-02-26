package com.example.internship.service;

import com.example.internship.dto.evaluation.*;
import com.example.internship.mapper.EvaluationMapper;
import com.example.internship.model.Evaluation;
import com.example.internship.model.Student;
import com.example.internship.model.Internship;
import com.example.internship.repository.EvaluationRepository;
import com.example.internship.repository.StudentRepository;
import com.example.internship.repository.InternshipRepository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService {
    
    private final EvaluationRepository evaluationRepository;
    private final StudentRepository studentRepository;
    private final InternshipRepository internshipRepository;

    public EvaluationService(EvaluationRepository evaluationRepository,
                             StudentRepository studentRepository,
                             InternshipRepository internshipRepository) {
        this.evaluationRepository = evaluationRepository;
        this.studentRepository = studentRepository;
        this.internshipRepository = internshipRepository;
    }

    public Page<EvaluationResponseDTO> getAllEvaluations(Pageable pageable) {
        return evaluationRepository.findAll(pageable)
                .map(EvaluationMapper::toResponseDTO);
    }

    public Page<EvaluationResponseDTO> searchByStudentIndexNumber(String indexNumber, Pageable pageable) {
        return evaluationRepository.findByStudentIndexNumberContainingIgnoreCase(indexNumber, pageable)
                .map(EvaluationMapper::toResponseDTO);
    }

    public EvaluationResponseDTO getById(Long id) {
        Evaluation eval = evaluationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluation not found with id: " + id));
        return EvaluationMapper.toResponseDTO(eval);
    }

    public EvaluationResponseDTO create(EvaluationRequestDTO dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + dto.getStudentId()));
        Internship internship = internshipRepository.findById(dto.getInternshipId())
                .orElseThrow(() -> new RuntimeException("Internship not found with id: " + dto.getInternshipId()));
        Evaluation entity = EvaluationMapper.toEntity(dto, student, internship);
        return EvaluationMapper.toResponseDTO(evaluationRepository.save(entity));
    }

    public EvaluationResponseDTO update(Long id, EvaluationRequestDTO dto) {
        Evaluation existing = evaluationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluation not found with id: " + id));
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + dto.getStudentId()));
        Internship internship = internshipRepository.findById(dto.getInternshipId())
                .orElseThrow(() -> new RuntimeException("Internship not found with id: " + dto.getInternshipId()));

        EvaluationMapper.updateEntity(existing, dto);
        existing.setStudent(student);
        existing.setInternship(internship);

        return EvaluationMapper.toResponseDTO(evaluationRepository.save(existing));
    }

    public void deleteById(Long id) {
        evaluationRepository.deleteById(id);
    }
}
