package com.example.internship.controller;

import com.example.internship.dto.company.*;
import com.example.internship.service.CompanyService;

import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<Page<CompanyResponseDTO>> getAllCompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(
                companyService.getAllCompanies(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CompanyResponseDTO>> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(
                companyService.searchByName(name, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                companyService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CompanyResponseDTO> create(
            @Valid @RequestBody CompanyRequestDTO dto) {

        CompanyResponseDTO created = companyService.create(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CompanyRequestDTO dto) {

        return ResponseEntity.ok(
                companyService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        companyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<CompanyResponseDTO> changeStatus(
            @PathVariable Long id,
            @RequestBody Map<String, Boolean> body) {

        Boolean approved = body.get("approved");
        if (approved == null) {
            return ResponseEntity.badRequest().build();
        }

        CompanyResponseDTO updated = companyService.changeStatus(id, approved);
        return ResponseEntity.ok(updated);
    }
}