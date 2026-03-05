package com.example.internship.service;

import com.example.internship.dto.cvinterest.*;
import com.example.internship.mapper.CVInterestMapper;
import com.example.internship.model.CV;
import com.example.internship.model.CVInterest;
import com.example.internship.repository.CVInterestRepository;
import com.example.internship.repository.CVRepository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class CVInterestService {

    private final CVInterestRepository interestRepository;
    private final CVRepository cvRepository;

    public CVInterestService(CVInterestRepository interestRepository,
                             CVRepository cvRepository) {
        this.interestRepository = interestRepository;
        this.cvRepository = cvRepository;
    }

    public Page<CVInterestResponseDTO> getAllInterests(Pageable pageable) {
        return interestRepository.findAll(pageable)
                .map(CVInterestMapper::toResponseDTO);
    }

    public Page<CVInterestResponseDTO> searchByName(String name, Pageable pageable) {
        return interestRepository
                .findByNameContainingIgnoreCase(name, pageable)
                .map(CVInterestMapper::toResponseDTO);
    }

    public CVInterestResponseDTO getById(Long id) {
        CVInterest cvInterest = interestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interest not found with id: " + id));

        return CVInterestMapper.toResponseDTO(cvInterest);
    }

    public CVInterestResponseDTO create(CVInterestRequestDTO dto) {

        CV cv = cvRepository.findById(dto.getCvId())
                .orElseThrow(() -> new RuntimeException("CV not found"));

        CVInterest entity = CVInterestMapper.toEntity(dto, cv);

        return CVInterestMapper.toResponseDTO(
                interestRepository.save(entity)
        );
    }

    public CVInterestResponseDTO update(Long id, CVInterestRequestDTO dto) {

        CVInterest existing = interestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interest not found with id: " + id));

        CVInterestMapper.updateEntity(existing, dto);

        return CVInterestMapper.toResponseDTO(
                interestRepository.save(existing)
        );
    }

    public void deleteById(Long id) {
        interestRepository.deleteById(id);
    }
}