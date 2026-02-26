package com.example.internship.controller;

import com.example.internship.dto.facultyadmin.*;
import com.example.internship.service.FacultyAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/faculty-admins")
@CrossOrigin
public class FacultyAdminController {

    private final FacultyAdminService facultyAdminService;

    @Autowired
    public FacultyAdminController(FacultyAdminService facultyAdminService) {
        this.facultyAdminService = facultyAdminService;
    }

    @GetMapping
    public Page<FacultyAdminResponseDTO> getAllFacultyAdmins(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return facultyAdminService.getAllFacultyAdmins(pageable);
    }

    @GetMapping("/search")
    public Page<FacultyAdminResponseDTO> searchByFacultyName(
            @RequestParam String facultyName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return facultyAdminService.searchByFacultyName(facultyName, pageable);
    }

    @GetMapping("/{id}")
    public FacultyAdminResponseDTO getById(@PathVariable Long id) {
        return facultyAdminService.getById(id);
    }

    @PostMapping
    public FacultyAdminResponseDTO create(@RequestBody FacultyAdminRequestDTO dto) {
        return facultyAdminService.create(dto);
    }

    @PutMapping("/{id}")
    public FacultyAdminResponseDTO update(@PathVariable Long id, @RequestBody FacultyAdminRequestDTO dto) {
        return facultyAdminService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        facultyAdminService.deleteById(id);
    }
}
