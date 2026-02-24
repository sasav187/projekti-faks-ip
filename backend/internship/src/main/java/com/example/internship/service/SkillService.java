package com.example.internship.service;

import com.example.internship.model.Skill;
import com.example.internship.repository.SkillRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class SkillService {
    
    private final SkillRepository skillRepository;

    @Autowired
    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Page<Skill> getAllSkills(Pageable pageable) {
        return skillRepository.findAll(pageable);
    }

    public Page<Skill> searchByName(String name, Pageable pageable) {
        return skillRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public Skill getById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found with id: " + id));
    }

    public Skill save(Skill skill) {
        return skillRepository.save(skill);
    }

    public void deleteById(Long id) {
        skillRepository.deleteById(id);
    }
}
