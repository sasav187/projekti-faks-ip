package com.example.internship.controller;

import com.example.internship.dto.recommendation.*;
import com.example.internship.service.RecommendationService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommendations")
@CrossOrigin
public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public Page<RecommendationResponseDTO> getAllRecommendations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return recommendationService.getAllRecommendations(pageable);
    }

    @GetMapping("/search")
    public Page<RecommendationResponseDTO> searchByExplanation(
            @RequestParam String explanation,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return recommendationService.searchByExplanation(explanation, pageable);
    }

    @GetMapping("/{id}")
    public RecommendationResponseDTO getById(@PathVariable Long id) {
        return recommendationService.getById(id);
    }

    @PostMapping
    public RecommendationResponseDTO create(@RequestBody RecommendationRequestDTO dto) {
        return recommendationService.create(dto);
    }

    @PutMapping("/{id}")
    public RecommendationResponseDTO update(@PathVariable Long id, @RequestBody RecommendationRequestDTO dto) {
        return recommendationService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        recommendationService.deleteById(id);
    }

    @PostMapping("/generate")
    public RecommendationResponseDTO generateRecommendation(
            @RequestParam Long internshipId,
            Authentication authentication) {

        String username = authentication.getName();

        return recommendationService.generateRecommendation(username, internshipId);
    }

    @GetMapping("/student")
    public List<RecommendationResponseDTO> getForStudent(Authentication authentication) {
        String username = authentication.getName();
        return recommendationService.generateRecommendationsForStudent(username);
    }
}
