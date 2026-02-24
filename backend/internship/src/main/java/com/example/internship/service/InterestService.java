package com.example.internship.service;

import com.example.internship.model.Interest;
import com.example.internship.repository.InterestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class InterestService {
    
    private final InterestRepository interestRepository;

    @Autowired
    public InterestService(InterestRepository interestRepository) {
        this.interestRepository = interestRepository;
    }

    public Page<Interest> getAllInterests(Pageable pageable) {
        return interestRepository.findAll(pageable);
    }

    public Page<Interest> searchByName(String name, Pageable pageable) {
        return interestRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public Interest getById(Long id) {
        return interestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interest not found with id: " + id));
    }

    public Interest save(Interest interest) {
        return interestRepository.save(interest);
    }

    public void deleteById(Long id) {
        interestRepository.deleteById(id);
    }
}
