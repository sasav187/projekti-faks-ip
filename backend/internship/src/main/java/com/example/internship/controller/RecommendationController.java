package com.example.internship.controller;

import com.example.internship.model.Recommendation;
import com.example.internship.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
    public Page<Recommendation> getAllRecommendations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return recommendationService.getAllRecommendations(pageable);
    }

    @GetMapping("/search")
    public Page<Recommendation> searchByExplanation(
            @RequestParam String explanation,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return recommendationService.searchByExplanation(explanation, pageable);
    }

    @GetMapping("/{id}")
    public Recommendation getById(@PathVariable Long id) {
        return recommendationService.getById(id);
    }

    @PostMapping
    public Recommendation create(@RequestBody Recommendation recommendation) {
        return recommendationService.save(recommendation);
    }

    @PutMapping("/{id}")
    public Recommendation update(@PathVariable Long id, @RequestBody Recommendation recommendation) {
        recommendation.setId(id);
        return recommendationService.save(recommendation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        recommendationService.deleteById(id);
    }
}
