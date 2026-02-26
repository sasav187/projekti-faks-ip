package com.example.internship.controller;

import com.example.internship.dto.skill.*;
import com.example.internship.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin
public class SkillController {

    private final SkillService skillService;

    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public Page<SkillResponseDTO> getAllSkills(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return skillService.getAllSkills(pageable);
    }

    @GetMapping("/search")
    public Page<SkillResponseDTO> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return skillService.searchByName(name, pageable);
    }

    @GetMapping("/{id}")
    public SkillResponseDTO getById(@PathVariable Long id) {
        return skillService.getById(id);
    }

    @PostMapping
    public SkillResponseDTO create(@RequestBody SkillRequestDTO dto) {
        return skillService.create(dto);
    }

    @PutMapping("/{id}")
    public SkillResponseDTO update(@PathVariable Long id, @RequestBody SkillRequestDTO dto) {
        return skillService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        skillService.deleteById(id);
    }
}
