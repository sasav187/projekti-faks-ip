package com.example.internship.service;

import com.example.internship.dto.skill.*;
import com.example.internship.mapper.SkillMapper;
import com.example.internship.model.Skill;
import com.example.internship.repository.SkillRepository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class SkillService {
    
    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Page<SkillResponseDTO> getAllSkills(Pageable pageable) {
        return skillRepository.findAll(pageable)
                .map(SkillMapper::toResponseDTO);
    }

    public Page<SkillResponseDTO> searchByName(String name, Pageable pageable) {
        return skillRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(SkillMapper::toResponseDTO);
    }

    public SkillResponseDTO getById(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found with id: " + id));
        return SkillMapper.toResponseDTO(skill);
    }

    public SkillResponseDTO create(SkillRequestDTO dto) {
        Skill entity = SkillMapper.toEntity(dto);
        return SkillMapper.toResponseDTO(skillRepository.save(entity));
    }

    public SkillResponseDTO update(Long id, SkillRequestDTO dto) {
        Skill existing = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found with id: " + id));
        SkillMapper.updateEntity(existing, dto);
        return SkillMapper.toResponseDTO(skillRepository.save(existing));
    }

    public void deleteById(Long id) {
        skillRepository.deleteById(id);
    }
}
