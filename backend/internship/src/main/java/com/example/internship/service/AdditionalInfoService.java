package com.example.internship.service;

import com.example.internship.dto.additionalinfo.*;
import com.example.internship.mapper.AdditionalInfoMapper;
import com.example.internship.model.AdditionalInfo;
import com.example.internship.model.CV;
import com.example.internship.repository.AdditionalInfoRepository;
import com.example.internship.repository.CVRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdditionalInfoService {

    private final AdditionalInfoRepository repo;
    private final CVRepository cvRepo;

    public AdditionalInfoService(AdditionalInfoRepository repo, CVRepository cvRepo) {
        this.repo = repo;
        this.cvRepo = cvRepo;
    }

    public AdditionalInfoResponseDTO create(AdditionalInfoRequestDTO dto) {
        CV cv = cvRepo.findById(dto.getCvId()).orElseThrow(() -> new RuntimeException("CV not found"));
        AdditionalInfo entity = AdditionalInfoMapper.toEntity(dto, cv);
        return AdditionalInfoMapper.toResponseDTO(repo.save(entity));
    }

    public AdditionalInfoResponseDTO update(Long id, AdditionalInfoRequestDTO dto) {
        AdditionalInfo entity = repo.findById(id).orElseThrow(() -> new RuntimeException("AdditionalInfo not found"));
        AdditionalInfoMapper.updateEntity(entity, dto);
        return AdditionalInfoMapper.toResponseDTO(repo.save(entity));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<AdditionalInfoResponseDTO> getAllByCv(Long cvId) {
        return repo.findAllByCvId(cvId)
                   .stream()
                   .map(AdditionalInfoMapper::toResponseDTO)
                   .collect(Collectors.toList());
    }
}