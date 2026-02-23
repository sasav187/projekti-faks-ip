package com.example.internship.service;

import com.example.internship.model.FacultyAdmin;
import com.example.internship.repository.FacultyAdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class FacultyAdminService {
    
    private final FacultyAdminRepository facultyAdminRepository;

    @Autowired
    public FacultyAdminService(FacultyAdminRepository facultyAdminRepository) {
        this.facultyAdminRepository = facultyAdminRepository;
    }

    public Page<FacultyAdmin> getAllFacultyAdmins(Pageable pageable) {
        return facultyAdminRepository.findAll(pageable);
    }

    public Page<FacultyAdmin> searchByFacultyName(String facultyName, Pageable pageable) {
        return facultyAdminRepository.findByFacultyNameContainingIgnoreCase(facultyName, pageable);
    }

    public FacultyAdmin getById(Long id) {
        return facultyAdminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FacultyAdmin not found with id: " + id));
    }

    public FacultyAdmin save(FacultyAdmin facultyAdmin) {
        return facultyAdminRepository.save(facultyAdmin);
    }

    public void deleteById(Long id) {
        facultyAdminRepository.deleteById(id);
    }
}
