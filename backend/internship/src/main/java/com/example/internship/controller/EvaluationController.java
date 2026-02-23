package com.example.internship.controller;

import com.example.internship.model.Evaluation;
import com.example.internship.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evaluations")
@CrossOrigin
public class EvaluationController {

    private final EvaluationService evaluationService;

    @Autowired
    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping
    public Page<Evaluation> getAllEvaluations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return evaluationService.getAllEvaluations(pageable);
    }

    @GetMapping("/search")
    public Page<Evaluation> searchByStudentIndexNumber(
            @RequestParam String indexNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return evaluationService.searchByStudentIndexNumber(indexNumber, pageable);
    }

    @GetMapping("/{id}")
    public Evaluation getById(@PathVariable Long id) {
        return evaluationService.getById(id);
    }

    @PostMapping
    public Evaluation create(@RequestBody Evaluation evaluation) {
        return evaluationService.save(evaluation);
    }

    @PutMapping("/{id}")
    public Evaluation update(@PathVariable Long id, @RequestBody Evaluation evaluation) {
        evaluation.setId(id);
        return evaluationService.save(evaluation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        evaluationService.deleteById(id);
    }
}
