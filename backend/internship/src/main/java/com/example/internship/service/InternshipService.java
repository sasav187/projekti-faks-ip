package com.example.internship.service;

import com.example.internship.model.Internship;
import com.example.internship.repository.InternshipRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class InternshipService {
    
    private final InternshipRepository internshipRepository;

    @Autowired
    public InternshipService(InternshipRepository internshipRepository) {
        this.internshipRepository = internshipRepository;
    }

    public Page<Internship> getAllInternships(Pageable pageable) {
        return internshipRepository.findAll(pageable);
    }

    public Page<Internship> searchByTitle(String title, Pageable pageable) {
        return internshipRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    public Internship getById(Long id) {
        return internshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Internship not found with id: " + id));
    }

    public Internship save(Internship internship) {
        return internshipRepository.save(internship);
    }

    public void deleteById(Long id) {
        internshipRepository.deleteById(id);
    }
}
