package com.example.internship.controller;

import com.example.internship.model.Education;
import com.example.internship.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/educations")
@CrossOrigin
public class EducationController {

    private final EducationService educationService;

    @Autowired
    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @GetMapping
    public Page<Education> getAllEducations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return educationService.getAllEducations(pageable);
    }

    @GetMapping("/search")
    public Page<Education> searchByInstitution(
            @RequestParam String institution,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return educationService.searchByInstitution(institution, pageable);
    }

    @GetMapping("/{id}")
    public Education getById(@PathVariable Long id) {
        return educationService.getById(id);
    }

    @PostMapping
    public Education create(@RequestBody Education education) {
        return educationService.save(education);
    }

    @PutMapping("/{id}")
    public Education update(@PathVariable Long id, @RequestBody Education education) {
        education.setId(id);
        return educationService.save(education);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        educationService.deleteById(id);
    }
}
