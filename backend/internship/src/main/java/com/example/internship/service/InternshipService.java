package com.example.internship.service;

import com.example.internship.dto.internship.*;
import com.example.internship.mapper.InternshipMapper;
import com.example.internship.model.Internship;
import com.example.internship.model.Company;
import com.example.internship.repository.InternshipRepository;
import com.example.internship.repository.CompanyRepository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class InternshipService {

    private final InternshipRepository internshipRepository;
    private final CompanyRepository companyRepository;

    public InternshipService(InternshipRepository internshipRepository,
            CompanyRepository companyRepository) {
        this.internshipRepository = internshipRepository;
        this.companyRepository = companyRepository;
    }

    public Page<InternshipResponseDTO> getAllInternships(Pageable pageable) {
        return internshipRepository.findAll(pageable)
                .map(InternshipMapper::toResponseDTO);
    }

    public Page<InternshipResponseDTO> searchByTitle(String title, Pageable pageable) {
        return internshipRepository.findByTitleContainingIgnoreCase(title, pageable)
                .map(InternshipMapper::toResponseDTO);
    }

    public InternshipResponseDTO getById(Long id) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Internship not found with id: " + id));
        return InternshipMapper.toResponseDTO(internship);
    }

    public InternshipResponseDTO create(InternshipRequestDTO dto) {
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + dto.getCompanyId()));
        Internship entity = InternshipMapper.toEntity(dto, company);
        return InternshipMapper.toResponseDTO(internshipRepository.save(entity));
    }

    public InternshipResponseDTO update(Long id, InternshipRequestDTO dto) {
        Internship existing = internshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Internship not found with id: " + id));
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + dto.getCompanyId()));
        InternshipMapper.updateEntity(existing, dto);
        existing.setCompany(company);
        return InternshipMapper.toResponseDTO(internshipRepository.save(existing));
    }

    public void deleteById(Long id) {
        internshipRepository.deleteById(id);
    }

    public Page<InternshipResponseDTO> search(
            String title,
            String technology,
            Pageable pageable) {

        Page<Internship> result;

        if (title != null && technology != null) {
            result = internshipRepository
                    .findByTitleContainingIgnoreCaseAndTechnologies_NameContainingIgnoreCase(
                            title,
                            technology,
                            pageable);
        } else if (title != null) {
            result = internshipRepository
                    .findByTitleContainingIgnoreCase(title, pageable);
        } else if (technology != null) {
            result = internshipRepository
                    .findByTechnologies_NameContainingIgnoreCase(technology, pageable);
        } else {
            result = internshipRepository.findAll(pageable);
        }

        return result.map(InternshipMapper::toResponseDTO);
    }
}
