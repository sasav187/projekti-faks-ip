package com.example.internship.controller;

import com.example.internship.model.Internship;
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
    public Page<Internship> getAllInternships(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return internshipService.getAllInternships(pageable);
    }

    @GetMapping("/search")
    public Page<Internship> searchByTitle(
            @RequestParam String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return internshipService.searchByTitle(title, pageable);
    }

    @GetMapping("/{id}")
    public Internship getById(@PathVariable Long id) {
        return internshipService.getById(id);
    }

    @PostMapping
    public Internship create(@RequestBody Internship internship) {
        return internshipService.save(internship);
    }

    @PutMapping("/{id}")
    public Internship update(@PathVariable Long id, @RequestBody Internship internship) {
        internship.setId(id);
        return internshipService.save(internship);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        internshipService.deleteById(id);
    }
}
