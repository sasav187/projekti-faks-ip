package com.example.internship.controller;

import com.example.internship.dto.internshipapplication.*;
import com.example.internship.service.InternshipApplicationService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
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
    public Page<InternshipApplicationResponseDTO> getAllInternshipApplications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return internshipApplicationService.getAllInternshipApplications(pageable);
    }

    @GetMapping("/search")
    public Page<InternshipApplicationResponseDTO> searchByApplicationMessage(
            @RequestParam String applicationMessage,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return internshipApplicationService.searchByMessage(applicationMessage, pageable);
    }

    @GetMapping("/{id}")
    public InternshipApplicationResponseDTO getById(@PathVariable Long id) {
        return internshipApplicationService.getById(id);
    }

    @PostMapping
    public InternshipApplicationResponseDTO create(
            @RequestBody InternshipApplicationRequestDTO dto,
            Authentication authentication) {
        return internshipApplicationService.create(dto, authentication.getName());
    }

    @PutMapping("/{id}")
    public InternshipApplicationResponseDTO update(@PathVariable Long id,
            @RequestBody InternshipApplicationRequestDTO dto) {
        return internshipApplicationService.update(id, dto);
    }

    @GetMapping("/my")
    public List<InternshipApplicationResponseDTO> getMyApplications(Authentication authentication) {
        String username = authentication.getName();
        return internshipApplicationService.getApplicationsByStudent(username);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        internshipApplicationService.deleteById(id);
    }
}
