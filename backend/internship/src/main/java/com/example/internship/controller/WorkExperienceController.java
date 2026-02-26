package com.example.internship.controller;

import com.example.internship.dto.workexperience.*;
import com.example.internship.service.WorkExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/work-experiences")
@CrossOrigin
public class WorkExperienceController {

    private final WorkExperienceService workExperienceService;

    @Autowired
    public WorkExperienceController(WorkExperienceService workExperienceService) {
        this.workExperienceService = workExperienceService;
    }

    @GetMapping
    public Page<WorkExperienceResponseDTO> getAllWorkExperiences(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return workExperienceService.getAllWorkExperiences(pageable);
    }

    @GetMapping("/search")
    public Page<WorkExperienceResponseDTO> searchByCompanyName(
            @RequestParam String companyName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return workExperienceService.searchByCompanyName(companyName, pageable);
    }

    @GetMapping("/{id}")
    public WorkExperienceResponseDTO getById(@PathVariable Long id) {
        return workExperienceService.getById(id);
    }

    @PostMapping
    public WorkExperienceResponseDTO create(@RequestBody WorkExperienceRequestDTO dto) {
        return workExperienceService.create(dto);
    }

    @PutMapping("/{id}")
    public WorkExperienceResponseDTO update(@PathVariable Long id, @RequestBody WorkExperienceRequestDTO dto) {
        return workExperienceService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        workExperienceService.deleteById(id);
    }
}
