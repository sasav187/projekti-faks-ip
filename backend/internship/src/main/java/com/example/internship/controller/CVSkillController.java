package com.example.internship.controller;

import com.example.internship.dto.cvskill.*;
import com.example.internship.service.CVSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cv-skills")
@CrossOrigin
public class CVSkillController {

    private final CVSkillService skillService;

    @Autowired
    public CVSkillController(CVSkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public Page<CVSkillResponseDTO> getAllSkills(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return skillService.getAllSkills(pageable);
    }

    @GetMapping("/search")
    public Page<CVSkillResponseDTO> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return skillService.searchByName(name, pageable);
    }

    @GetMapping("/{id}")
    public CVSkillResponseDTO getById(@PathVariable Long id) {
        return skillService.getById(id);
    }

    @PostMapping
    public CVSkillResponseDTO create(@RequestBody CVSkillRequestDTO dto) {
        return skillService.create(dto);
    }

    @PutMapping("/{id}")
    public CVSkillResponseDTO update(@PathVariable Long id, @RequestBody CVSkillRequestDTO dto) {
        return skillService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        skillService.deleteById(id);
    }
}
