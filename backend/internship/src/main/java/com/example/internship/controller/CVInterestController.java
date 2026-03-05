package com.example.internship.controller;

import com.example.internship.dto.cvinterest.*;
import com.example.internship.service.CVInterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cv-interests")
@CrossOrigin
public class CVInterestController {

    private final CVInterestService interestService;

    @Autowired
    public CVInterestController(CVInterestService interestService) {
        this.interestService = interestService;
    }

    @GetMapping
    public Page<CVInterestResponseDTO> getAllInterests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return interestService.getAllInterests(pageable);
    }

    @GetMapping("/search")
    public Page<CVInterestResponseDTO> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return interestService.searchByName(name, pageable);
    }

    @GetMapping("/{id}")
    public CVInterestResponseDTO getById(@PathVariable Long id) {
        return interestService.getById(id);
    }

    @PostMapping
    public CVInterestResponseDTO create(@RequestBody CVInterestRequestDTO dto) {
        return interestService.create(dto);
    }

    @PutMapping("/{id}")
    public CVInterestResponseDTO update(@PathVariable Long id, @RequestBody CVInterestRequestDTO dto) {
        return interestService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        interestService.deleteById(id);
    }   
}
