package com.example.internship.controller;

import com.example.internship.model.FacultyAdmin;
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
    public Page<FacultyAdmin> getAllFacultyAdmins(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return facultyAdminService.getAllFacultyAdmins(pageable);
    }

    @GetMapping("/search")
    public Page<FacultyAdmin> searchByFacultyName(
            @RequestParam String facultyName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return facultyAdminService.searchByFacultyName(facultyName, pageable);
    }

    @GetMapping("/{id}")
    public FacultyAdmin getById(@PathVariable Long id) {
        return facultyAdminService.getById(id);
    }

    @PostMapping
    public FacultyAdmin create(@RequestBody FacultyAdmin facultyAdmin) {
        return facultyAdminService.save(facultyAdmin);
    }

    @PutMapping("/{id}")
    public FacultyAdmin update(@PathVariable Long id, @RequestBody FacultyAdmin facultyAdmin) {
        facultyAdmin.setId(id);
        return facultyAdminService.save(facultyAdmin);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        facultyAdminService.deleteById(id);
    }
}
