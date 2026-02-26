package com.example.internship.controller;

import com.example.internship.dto.evaluation.*;
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
    public Page<EvaluationResponseDTO> getAllEvaluations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return evaluationService.getAllEvaluations(pageable);
    }

    @GetMapping("/search")
    public Page<EvaluationResponseDTO> searchByStudentIndexNumber(
            @RequestParam String indexNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return evaluationService.searchByStudentIndexNumber(indexNumber, pageable);
    }

    @GetMapping("/{id}")
    public EvaluationResponseDTO getById(@PathVariable Long id) {
        return evaluationService.getById(id);
    }

    @PostMapping
    public EvaluationResponseDTO create(@RequestBody EvaluationRequestDTO dto) {
        return evaluationService.create(dto);
    }

    @PutMapping("/{id}")
    public EvaluationResponseDTO update(@PathVariable Long id, @RequestBody EvaluationRequestDTO dto) {
        return evaluationService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        evaluationService.deleteById(id);
    }
}
