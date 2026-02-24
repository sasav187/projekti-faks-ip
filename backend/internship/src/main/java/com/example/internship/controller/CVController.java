package com.example.internship.controller;

import com.example.internship.model.CV;
import com.example.internship.service.CVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cvs")
@CrossOrigin
public class CVController {

    private final CVService cvService;

    @Autowired
    public CVController(CVService cvService) {
        this.cvService = cvService;
    }

    @GetMapping
    public Page<CV> getAllCVs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cvService.getAllCVs(pageable);
    }

    @GetMapping("/search")
    public Page<CV> searchBySummary(
            @RequestParam String summary,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cvService.searchBySummary(summary, pageable);
    }

    @GetMapping("/{id}")
    public CV getById(@PathVariable Long id) {
        return cvService.getById(id);
    }

    @PostMapping
    public CV create(@RequestBody CV cv) {
        return cvService.save(cv);
    }

    @PutMapping("/{id}")
    public CV update(@PathVariable Long id, @RequestBody CV cv) {
        cv.setId(id);
        return cvService.save(cv);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cvService.deleteById(id);
    }   
}
