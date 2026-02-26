package com.example.internship.controller;

import com.example.internship.dto.cv.*;
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
    public Page<CVResponseDTO> getAllCVs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cvService.getAllCVs(pageable);
    }

    @GetMapping("/search")
    public Page<CVResponseDTO> searchBySummary(
            @RequestParam String summary,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cvService.searchBySummary(summary, pageable);
    }

    @GetMapping("/{id}")
    public CVResponseDTO getById(@PathVariable Long id) {
        return cvService.getById(id);
    }

    @PostMapping
    public CVResponseDTO create(@RequestBody CVRequestDTO dto) {
        return cvService.create(dto);
    }

    @PutMapping("/{id}")
    public CVResponseDTO update(@PathVariable Long id, @RequestBody CVRequestDTO dto) {
        return cvService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cvService.deleteById(id);
    }   
}
