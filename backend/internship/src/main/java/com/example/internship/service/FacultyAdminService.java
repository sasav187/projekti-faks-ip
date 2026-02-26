package com.example.internship.service;

import com.example.internship.dto.facultyadmin.*;
import com.example.internship.mapper.FacultyAdminMapper;
import com.example.internship.model.FacultyAdmin;
import com.example.internship.model.User;
import com.example.internship.repository.FacultyAdminRepository;
import com.example.internship.repository.UserRepository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class FacultyAdminService {
    
    private final FacultyAdminRepository facultyAdminRepository;
    private final UserRepository userRepository;

    public FacultyAdminService(FacultyAdminRepository facultyAdminRepository,
                               UserRepository userRepository) {
        this.facultyAdminRepository = facultyAdminRepository;
        this.userRepository = userRepository;
    }

    public Page<FacultyAdminResponseDTO> getAllFacultyAdmins(Pageable pageable) {
        return facultyAdminRepository.findAll(pageable)
                .map(FacultyAdminMapper::toResponseDTO);
    }

    public Page<FacultyAdminResponseDTO> searchByFacultyName(String facultyName, Pageable pageable) {
        return facultyAdminRepository.findByFacultyNameContainingIgnoreCase(facultyName, pageable)
                .map(FacultyAdminMapper::toResponseDTO);
    }

    public FacultyAdminResponseDTO getById(Long id) {
        FacultyAdmin fa = facultyAdminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FacultyAdmin not found with id: " + id));
        return FacultyAdminMapper.toResponseDTO(fa);
    }

    public FacultyAdminResponseDTO create(FacultyAdminRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
        FacultyAdmin entity = FacultyAdminMapper.toEntity(dto, user);
        return FacultyAdminMapper.toResponseDTO(facultyAdminRepository.save(entity));
    }

    public FacultyAdminResponseDTO update(Long id, FacultyAdminRequestDTO dto) {
        FacultyAdmin existing = facultyAdminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FacultyAdmin not found with id: " + id));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
        FacultyAdminMapper.updateEntity(existing, dto);
        existing.setUser(user);
        return FacultyAdminMapper.toResponseDTO(facultyAdminRepository.save(existing));
    }

    public void deleteById(Long id) {
        facultyAdminRepository.deleteById(id);
    }
}
