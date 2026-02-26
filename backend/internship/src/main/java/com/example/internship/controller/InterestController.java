package com.example.internship.controller;

import com.example.internship.dto.interest.*;
import com.example.internship.service.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interests")
@CrossOrigin
public class InterestController {

    private final InterestService interestService;

    @Autowired
    public InterestController(InterestService interestService) {
        this.interestService = interestService;
    }

    @GetMapping
    public Page<InterestResponseDTO> getAllInterests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return interestService.getAllInterests(pageable);
    }

    @GetMapping("/search")
    public Page<InterestResponseDTO> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return interestService.searchByName(name, pageable);
    }

    @GetMapping("/{id}")
    public InterestResponseDTO getById(@PathVariable Long id) {
        return interestService.getById(id);
    }

    @PostMapping
    public InterestResponseDTO create(@RequestBody InterestRequestDTO dto) {
        return interestService.create(dto);
    }

    @PutMapping("/{id}")
    public InterestResponseDTO update(@PathVariable Long id, @RequestBody InterestRequestDTO dto) {
        return interestService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        interestService.deleteById(id);
    }   
}
