package com.example.internship.controller;

import com.example.internship.model.InternshipApplication;
import com.example.internship.service.InternshipApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/internship-applications")
@CrossOrigin
public class InternshipApplicationController {

    private final InternshipApplicationService internshipApplicationService;

    @Autowired
    public InternshipApplicationController(InternshipApplicationService internshipApplicationService) {
        this.internshipApplicationService = internshipApplicationService;
    }

    @GetMapping
    public Page<InternshipApplication> getAllInternshipApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return internshipApplicationService.getAllInternshipApplications(pageable);
    }

    @GetMapping("/search")
    public Page<InternshipApplication> searchByApplicationMessage(
            @RequestParam String applicationMessage,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return internshipApplicationService.searchByMessage(applicationMessage, pageable);
    }

    @GetMapping("/{id}")
    public InternshipApplication getById(@PathVariable Long id) {
        return internshipApplicationService.getById(id);
    }

    @PostMapping
    public InternshipApplication create(@RequestBody InternshipApplication internshipApplication) {
        return internshipApplicationService.save(internshipApplication);
    }

    @PutMapping("/{id}")
    public InternshipApplication update(@PathVariable Long id, @RequestBody InternshipApplication internshipApplication) {
        internshipApplication.setId(id);
        return internshipApplicationService.save(internshipApplication);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        internshipApplicationService.deleteById(id);
    }   
}
