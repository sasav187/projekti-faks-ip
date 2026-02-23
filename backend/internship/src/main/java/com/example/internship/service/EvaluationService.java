package com.example.internship.service;

import com.example.internship.model.Evaluation;
import com.example.internship.repository.EvaluationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class EvaluationService {
    
    private final EvaluationRepository evaluationRepository;

    @Autowired
    public EvaluationService(EvaluationRepository evaluationRepository) {
        this.evaluationRepository = evaluationRepository;
    }

    public Page<Evaluation> getAllEvaluations(Pageable pageable) {
        return evaluationRepository.findAll(pageable);
    }

    public Page<Evaluation> searchByStudentIndexNumber(String indexNumber, Pageable pageable) {
        return evaluationRepository.findByStudentIndexNumberContainingIgnoreCase(indexNumber, pageable);
    }

    public Evaluation getById(Long id) {
        return evaluationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evaluation not found with id: " + id));
    }

    public Evaluation save(Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    public void deleteById(Long id) {
        evaluationRepository.deleteById(id);
    }
}
