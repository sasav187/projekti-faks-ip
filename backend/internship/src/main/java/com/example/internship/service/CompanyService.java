package com.example.internship.service;

import com.example.internship.dto.company.*;
import com.example.internship.mapper.CompanyMapper;
import com.example.internship.model.Company;
import com.example.internship.model.User;
import com.example.internship.repository.CompanyRepository;
import com.example.internship.repository.UserRepository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public CompanyService(CompanyRepository companyRepository,
                          UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public Page<CompanyResponseDTO> getAllCompanies(Pageable pageable) {
        return companyRepository.findAll(pageable)
                .map(CompanyMapper::toResponseDTO);
    }

    public Page<CompanyResponseDTO> searchByName(String name, Pageable pageable) {
        return companyRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(CompanyMapper::toResponseDTO);
    }

    public CompanyResponseDTO getById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Company not found with id: " + id));

        return CompanyMapper.toResponseDTO(company);
    }

    public CompanyResponseDTO create(CompanyRequestDTO dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + dto.getUserId()));

        Company company = CompanyMapper.toEntity(dto, user);

        return CompanyMapper.toResponseDTO(
                companyRepository.save(company)
        );
    }

    public CompanyResponseDTO update(Long id, CompanyRequestDTO dto) {

        Company company = companyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Company not found with id: " + id));

        CompanyMapper.updateEntity(company, dto);

        return CompanyMapper.toResponseDTO(
                companyRepository.save(company)
        );
    }

    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }
}
