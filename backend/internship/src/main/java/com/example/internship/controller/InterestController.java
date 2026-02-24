package com.example.internship.controller;

import com.example.internship.model.Interest;
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
    public Page<Interest> getAllInterests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return interestService.getAllInterests(pageable);
    }

    @GetMapping("/search")
    public Page<Interest> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return interestService.searchByName(name, pageable);
    }

    @GetMapping("/{id}")
    public Interest getById(@PathVariable Long id) {
        return interestService.getById(id);
    }

    @PostMapping
    public Interest create(@RequestBody Interest interest) {
        return interestService.save(interest);
    }

    @PutMapping("/{id}")
    public Interest update(@PathVariable Long id, @RequestBody Interest interest) {
        interest.setId(id);
        return interestService.save(interest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        interestService.deleteById(id);
    }   
}
