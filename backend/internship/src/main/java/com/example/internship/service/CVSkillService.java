package com.example.internship.service;

import com.example.internship.dto.cvskill.*;
import com.example.internship.mapper.CVSkillMapper;
import com.example.internship.model.CVSkill;
import com.example.internship.repository.CVSkillRepository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class CVSkillService {
    
    private final CVSkillRepository skillRepository;

    public CVSkillService(CVSkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Page<CVSkillResponseDTO> getAllSkills(Pageable pageable) {
        return skillRepository.findAll(pageable)
                .map(CVSkillMapper::toResponseDTO);
    }

    public Page<CVSkillResponseDTO> searchByName(String name, Pageable pageable) {
        return skillRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(CVSkillMapper::toResponseDTO);
    }

    public CVSkillResponseDTO getById(Long id) {
        CVSkill cvSkill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found with id: " + id));
        return CVSkillMapper.toResponseDTO(cvSkill);
    }

    public CVSkillResponseDTO create(CVSkillRequestDTO dto) {
        CVSkill entity = CVSkillMapper.toEntity(dto);
        return CVSkillMapper.toResponseDTO(skillRepository.save(entity));
    }

    public CVSkillResponseDTO update(Long id, CVSkillRequestDTO dto) {
        CVSkill existing = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found with id: " + id));
        CVSkillMapper.updateEntity(existing, dto);
        return CVSkillMapper.toResponseDTO(skillRepository.save(existing));
    }

    public void deleteById(Long id) {   
        skillRepository.deleteById(id);
    }
}
