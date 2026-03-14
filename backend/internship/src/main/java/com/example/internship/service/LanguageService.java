package com.example.internship.service;

import com.example.internship.dto.language.*;
import com.example.internship.mapper.LanguageMapper;
import com.example.internship.model.Language;
import com.example.internship.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanguageService {

    private final LanguageRepository repo;

    public LanguageService(LanguageRepository repo) {
        this.repo = repo;
    }

    public LanguageResponseDTO create(LanguageRequestDTO dto) {
        Language entity = LanguageMapper.toEntity(dto);
        return LanguageMapper.toResponseDTO(repo.save(entity));
    }

    public LanguageResponseDTO update(Long id, LanguageRequestDTO dto) {
        Language entity = repo.findById(id).orElseThrow(() -> new RuntimeException("Language not found"));
        LanguageMapper.updateEntity(entity, dto);
        return LanguageMapper.toResponseDTO(repo.save(entity));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<LanguageResponseDTO> getAllByCv(Long cvId) {
        return repo.findAllByCvId(cvId)
                   .stream()
                   .map(LanguageMapper::toResponseDTO)
                   .collect(Collectors.toList());
    }
}