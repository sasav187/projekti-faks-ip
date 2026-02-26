package com.example.internship.service;

import com.example.internship.dto.interest.*;
import com.example.internship.mapper.InterestMapper;
import com.example.internship.model.Interest;
import com.example.internship.repository.InterestRepository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class InterestService {
    
    private final InterestRepository interestRepository;

    public InterestService(InterestRepository interestRepository) {
        this.interestRepository = interestRepository;
    }

    public Page<InterestResponseDTO> getAllInterests(Pageable pageable) {
        return interestRepository.findAll(pageable)
                .map(InterestMapper::toResponseDTO);
    }

    public Page<InterestResponseDTO> searchByName(String name, Pageable pageable) {
        return interestRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(InterestMapper::toResponseDTO);
    }

    public InterestResponseDTO getById(Long id) {
        Interest interest = interestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interest not found with id: " + id));
        return InterestMapper.toResponseDTO(interest);
    }

    public InterestResponseDTO create(InterestRequestDTO dto) {
        Interest entity = InterestMapper.toEntity(dto);
        return InterestMapper.toResponseDTO(interestRepository.save(entity));
    }

    public InterestResponseDTO update(Long id, InterestRequestDTO dto) {
        Interest existing = interestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interest not found with id: " + id));
        InterestMapper.updateEntity(existing, dto);
        return InterestMapper.toResponseDTO(interestRepository.save(existing));
    }

    public void deleteById(Long id) {
        interestRepository.deleteById(id);
    }
}
