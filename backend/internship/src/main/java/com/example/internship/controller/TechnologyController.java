package com.example.internship.controller;

import com.example.internship.model.Technology;
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
    public Page<Technology> getAllTechnologies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return technologyService.getAllTechnologies(pageable);
    }

    @GetMapping("/search")
    public Page<Technology> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return technologyService.searchByName(name, pageable);
    }

    @GetMapping("/{id}")
    public Technology getById(@PathVariable Long id) {
        return technologyService.getById(id);
    }

    @PostMapping
    public Technology create(@RequestBody Technology technology) {
        return technologyService.save(technology);
    }

    @PutMapping("/{id}")
    public Technology update(@PathVariable Long id, @RequestBody Technology technology) {
        technology.setId(id);
        return technologyService.save(technology);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        technologyService.deleteById(id);
    }
}
