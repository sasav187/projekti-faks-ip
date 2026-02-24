package com.example.internship.service;

import com.example.internship.model.Education;
import com.example.internship.repository.EducationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class EducationService {
    
    private final EducationRepository educationRepository;

    @Autowired
    public EducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    public Page<Education> getAllEducations(Pageable pageable) {
        return educationRepository.findAll(pageable);
    }

    public Page<Education> searchByInstitution(String institution, Pageable pageable) {
        return educationRepository.findByInstitutionContainingIgnoreCase(institution, pageable);
    }

    public Education getById(Long id) {
        return educationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Education not found with id: " + id));
    }

    public Education save(Education education) {
        return educationRepository.save(education);
    }

    public void deleteById(Long id) {
        educationRepository.deleteById(id);
    }
}
