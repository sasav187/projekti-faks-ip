package com.example.internship.controller;

import com.example.internship.dto.internship.*;
import com.example.internship.service.InternshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/internships")
@CrossOrigin
public class InternshipController {

    private final InternshipService internshipService;

    @Autowired
    public InternshipController(InternshipService internshipService) {
        this.internshipService = internshipService;
    }

    @GetMapping
    public Page<InternshipResponseDTO> getAllInternships(Pageable pageable) {
        return internshipService.getAllInternships(pageable);
    }

    @GetMapping("/{id}")
    public InternshipResponseDTO getById(@PathVariable Long id) {
        return internshipService.getById(id);
    }

    @PostMapping
    public InternshipResponseDTO create(@RequestBody InternshipRequestDTO dto) {
        return internshipService.create(dto);
    }

    @PutMapping("/{id}")
    public InternshipResponseDTO update(@PathVariable Long id, @RequestBody InternshipRequestDTO dto) {
        return internshipService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        internshipService.deleteById(id);
    }

    @GetMapping("/search")
    public Page<InternshipResponseDTO> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String technology,
            Pageable pageable) {
        return internshipService.search(title, technology, pageable);
    }
}
