package com.example.internship.service;

import com.example.internship.model.WorkExperience;
import com.example.internship.repository.WorkExperienceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class WorkExperienceService {
    
    private final WorkExperienceRepository workExperienceRepository;

    @Autowired
    public WorkExperienceService(WorkExperienceRepository workExperienceRepository) {
        this.workExperienceRepository = workExperienceRepository;
    }

    public Page<WorkExperience> getAllWorkExperiences(Pageable pageable) {
        return workExperienceRepository.findAll(pageable);
    }

    public Page<WorkExperience> searchByCompanyName(String companyName, Pageable pageable) {
        return workExperienceRepository.findByCompanyNameContainingIgnoreCase(companyName, pageable);
    }

    public WorkExperience getById(Long id) {
        return workExperienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkExperience not found with id: " + id));
    }

    public WorkExperience save(WorkExperience workExperience) {
        return workExperienceRepository.save(workExperience);
    }

    public void deleteById(Long id) {
        workExperienceRepository.deleteById(id);
    }
}
