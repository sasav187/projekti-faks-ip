package com.example.internship.service;

import com.example.internship.dto.additionalinfo.*;
import com.example.internship.mapper.AdditionalInfoMapper;
import com.example.internship.model.AdditionalInfo;
import com.example.internship.repository.AdditionalInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdditionalInfoService {

    private final AdditionalInfoRepository repo;

    public AdditionalInfoService(AdditionalInfoRepository repo) {
        this.repo = repo;
    }

    public AdditionalInfoResponseDTO create(AdditionalInfoRequestDTO dto) {
        AdditionalInfo entity = AdditionalInfoMapper.toEntity(dto);
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