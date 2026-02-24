package com.example.internship.controller;

import com.example.internship.model.WorkExperience;
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
    public Page<WorkExperience> getAllWorkExperiences(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return workExperienceService.getAllWorkExperiences(pageable);
    }

    @GetMapping("/search")
    public Page<WorkExperience> searchByCompanyName(
            @RequestParam String companyName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return workExperienceService.searchByCompanyName(companyName, pageable);
    }

    @GetMapping("/{id}")
    public WorkExperience getById(@PathVariable Long id) {
        return workExperienceService.getById(id);
    }

    @PostMapping
    public WorkExperience create(@RequestBody WorkExperience workExperience) {
        return workExperienceService.save(workExperience);
    }

    @PutMapping("/{id}")
    public WorkExperience update(@PathVariable Long id, @RequestBody WorkExperience workExperience) {
        workExperience.setId(id);
        return workExperienceService.save(workExperience);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        workExperienceService.deleteById(id);
    }
}
