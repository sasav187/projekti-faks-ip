package com.example.internship.controller;

import com.example.internship.dto.technology.*;
import com.example.internship.service.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/technologies")
@CrossOrigin
public class TechnologyController {

    private final TechnologyService technologyService;

    @Autowired
    public TechnologyController(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    @GetMapping
    public Page<TechnologyResponseDTO> getAllTechnologies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return technologyService.getAllTechnologies(pageable);
    }

    @GetMapping("/search")
    public Page<TechnologyResponseDTO> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return technologyService.searchByName(name, pageable);
    }

    @GetMapping("/{id}")
    public TechnologyResponseDTO getById(@PathVariable Long id) {
        return technologyService.getById(id);
    }

    @PostMapping
    public TechnologyResponseDTO create(@RequestBody TechnologyRequestDTO dto) {
        return technologyService.create(dto);
    }

    @PutMapping("/{id}")
    public TechnologyResponseDTO update(@PathVariable Long id, @RequestBody TechnologyRequestDTO dto) {
        return technologyService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        technologyService.deleteById(id);
    }
}
