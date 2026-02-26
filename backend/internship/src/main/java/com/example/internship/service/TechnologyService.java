package com.example.internship.service;

import com.example.internship.dto.technology.*;
import com.example.internship.mapper.TechnologyMapper;
import com.example.internship.model.Technology;
import com.example.internship.repository.TechnologyRepository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class TechnologyService {
    
    private final TechnologyRepository technologyRepository;

    public TechnologyService(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    public Page<TechnologyResponseDTO> getAllTechnologies(Pageable pageable) {
        return technologyRepository.findAll(pageable)
                .map(TechnologyMapper::toResponseDTO);
    }

    public Page<TechnologyResponseDTO> searchByName(String name, Pageable pageable) {
        return technologyRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(TechnologyMapper::toResponseDTO);
    }

    public TechnologyResponseDTO getById(Long id) {
        Technology tech = technologyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Technology not found with id: " + id));
        return TechnologyMapper.toResponseDTO(tech);
    }

    public TechnologyResponseDTO create(TechnologyRequestDTO dto) {
        Technology entity = TechnologyMapper.toEntity(dto);
        return TechnologyMapper.toResponseDTO(technologyRepository.save(entity));
    }

    public TechnologyResponseDTO update(Long id, TechnologyRequestDTO dto) {
        Technology existing = technologyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Technology not found with id: " + id));
        TechnologyMapper.updateEntity(existing, dto);
        return TechnologyMapper.toResponseDTO(technologyRepository.save(existing));
    }

    public void deleteById(Long id) {
        technologyRepository.deleteById(id);
    }
}
