package com.example.internship.controller;

import com.example.internship.model.Company;
import com.example.internship.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public Page<Company> getAllCompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return companyService.getAllCompanies(pageable);
    }

    @GetMapping("/search")
    public Page<Company> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return companyService.searchByName(name, pageable);
    }

    @GetMapping("/{id}")
    public Company getById(@PathVariable Long id) {
        return companyService.getById(id);
    }

    @PostMapping
    public Company create(@RequestBody Company company) {
        return companyService.save(company);
    }

    @PutMapping("/{id}")
    public Company update(@PathVariable Long id, @RequestBody Company company) {
        company.setId(id);
        return companyService.save(company);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        companyService.deleteById(id);
    }
}
