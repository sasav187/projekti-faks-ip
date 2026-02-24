package com.example.internship.service;

import com.example.internship.model.Technology;
import com.example.internship.repository.TechnologyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class TechnologyService {
    
    private final TechnologyRepository technologyRepository;

    @Autowired
    public TechnologyService(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    public Page<Technology> getAllTechnologies(Pageable pageable) {
        return technologyRepository.findAll(pageable);
    }

    public Page<Technology> searchByName(String name, Pageable pageable) {
        return technologyRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public Technology getById(Long id) {
        return technologyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Technology not found with id: " + id));
    }

    public Technology save(Technology technology) {
        return technologyRepository.save(technology);
    }

    public void deleteById(Long id) {
        technologyRepository.deleteById(id);
    }
}
