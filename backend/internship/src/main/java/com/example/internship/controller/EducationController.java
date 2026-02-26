package com.example.internship.controller;

import com.example.internship.dto.education.*;
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
    public Page<EducationResponseDTO> getAllEducations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return educationService.getAllEducations(pageable);
    }

    @GetMapping("/search")
    public Page<EducationResponseDTO> searchByInstitution(
            @RequestParam String institution,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return educationService.searchByInstitution(institution, pageable);
    }

    @GetMapping("/{id}")
    public EducationResponseDTO getById(@PathVariable Long id) {
        return educationService.getById(id);
    }

    @PostMapping
    public EducationResponseDTO create(@RequestBody EducationRequestDTO dto) {
        return educationService.create(dto);
    }

    @PutMapping("/{id}")
    public EducationResponseDTO update(@PathVariable Long id, @RequestBody EducationRequestDTO dto) {
        return educationService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        educationService.deleteById(id);
    }
}
