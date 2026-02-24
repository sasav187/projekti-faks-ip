package com.example.internship.service;

import com.example.internship.model.CV;
import com.example.internship.repository.CVRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class CVService {
    
    private final CVRepository cvRepository;

    @Autowired
    public CVService(CVRepository cvRepository) {
        this.cvRepository = cvRepository;
    }

    public Page<CV> getAllCVs(Pageable pageable) {
        return cvRepository.findAll(pageable);
    }

    public Page<CV> searchBySummary(String summary, Pageable pageable) {
        return cvRepository.findBySummaryContainingIgnoreCase(summary, pageable);
    }

    public CV getById(Long id) {
        return cvRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CV not found with id: " + id));
    }

    public CV save(CV cv) {
        return cvRepository.save(cv);
    }

    public void deleteById(Long id) {
        cvRepository.deleteById(id);
    }
}
