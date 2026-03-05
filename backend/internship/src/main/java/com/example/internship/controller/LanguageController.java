package com.example.internship.controller;

import com.example.internship.dto.language.*;
import com.example.internship.service.LanguageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {

    private final LanguageService service;

    public LanguageController(LanguageService service) {
        this.service = service;
    }

    @PostMapping
    public LanguageResponseDTO create(@RequestBody LanguageRequestDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public LanguageResponseDTO update(@PathVariable Long id, @RequestBody LanguageRequestDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/cv/{cvId}")
    public List<LanguageResponseDTO> getAllByCv(@PathVariable Long cvId) {
        return service.getAllByCv(cvId);
    }
}