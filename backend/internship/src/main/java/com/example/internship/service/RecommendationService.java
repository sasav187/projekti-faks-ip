package com.example.internship.service;

import com.example.internship.model.Recommendation;
import com.example.internship.repository.RecommendationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class RecommendationService {
    
    private final RecommendationRepository recommendationRepository;

    @Autowired
    public RecommendationService(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    public Page<Recommendation> getAllRecommendations(Pageable pageable) {
        return recommendationRepository.findAll(pageable);
    }

    public Page<Recommendation> searchByExplanation(String explanation, Pageable pageable) {
        return recommendationRepository.findByExplanationContainingIgnoreCase(explanation, pageable);
    }

    public Recommendation getById(Long id) {
        return recommendationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recommendation not found with id: " + id));
    }

    public Recommendation save(Recommendation recommendation) {
        return recommendationRepository.save(recommendation);
    }

    public void deleteById(Long id) {
        recommendationRepository.deleteById(id);
    }
}
