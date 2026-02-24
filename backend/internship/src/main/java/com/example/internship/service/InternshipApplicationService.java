package com.example.internship.service;

import com.example.internship.model.InternshipApplication;
import com.example.internship.repository.InternshipApplicationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class InternshipApplicationService {
    
    private final InternshipApplicationRepository internshipApplicationRepository;

    @Autowired
    public InternshipApplicationService(InternshipApplicationRepository internshipApplicationRepository) {
        this.internshipApplicationRepository = internshipApplicationRepository;
    }

    public Page<InternshipApplication> getAllInternshipApplications(Pageable pageable) {
        return internshipApplicationRepository.findAll(pageable);
    }

    public Page<InternshipApplication> searchByMessage(String applicationMessage, Pageable pageable) {
        return internshipApplicationRepository.findByApplicationMessageContainingIgnoreCase(applicationMessage, pageable);
    }

    public InternshipApplication getById(Long id) {
        return internshipApplicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("InternshipApplication not found with id: " + id));
    }

    public InternshipApplication save(InternshipApplication internshipApplication) {
        return internshipApplicationRepository.save(internshipApplication);
    }

    public void deleteById(Long id) {
        internshipApplicationRepository.deleteById(id);
    }
}
