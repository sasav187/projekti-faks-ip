package com.example.internship.service;

import com.example.internship.dto.internshipapplication.*;
import com.example.internship.mapper.InternshipApplicationMapper;
import com.example.internship.model.InternshipApplication;
import com.example.internship.model.User;
import com.example.internship.model.Student;
import com.example.internship.model.Internship;
import com.example.internship.repository.InternshipApplicationRepository;
import com.example.internship.repository.UserRepository;
import com.example.internship.repository.StudentRepository;
import com.example.internship.repository.InternshipRepository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class InternshipApplicationService {

        private final InternshipApplicationRepository internshipApplicationRepository;
        private final UserRepository userRepository;
        private final StudentRepository studentRepository;
        private final InternshipRepository internshipRepository;

        public InternshipApplicationService(
                        InternshipApplicationRepository internshipApplicationRepository,
                        UserRepository userRepository,
                        StudentRepository studentRepository,
                        InternshipRepository internshipRepository) {
                this.internshipApplicationRepository = internshipApplicationRepository;
                this.userRepository = userRepository;
                this.studentRepository = studentRepository;
                this.internshipRepository = internshipRepository;
        }

        public Page<InternshipApplicationResponseDTO> getAllInternshipApplications(Pageable pageable) {
                return internshipApplicationRepository.findAll(pageable)
                                .map(InternshipApplicationMapper::toResponseDTO);
        }

        public Page<InternshipApplicationResponseDTO> searchByMessage(String applicationMessage, Pageable pageable) {
                return internshipApplicationRepository
                                .findByApplicationMessageContainingIgnoreCase(applicationMessage, pageable)
                                .map(InternshipApplicationMapper::toResponseDTO);
        }

        public InternshipApplicationResponseDTO getById(Long id) {
                InternshipApplication ia = internshipApplicationRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "InternshipApplication not found with id: " + id));
                return InternshipApplicationMapper.toResponseDTO(ia);
        }

        public InternshipApplicationResponseDTO create(
                        InternshipApplicationRequestDTO dto,
                        String username) {

                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                Student student = studentRepository.findByUserId(user.getId())
                                .orElseThrow(() -> new RuntimeException("Student not found for user"));

                Internship internship = internshipRepository.findById(dto.getInternshipId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Internship not found with id: " + dto.getInternshipId()));

                InternshipApplication entity = InternshipApplicationMapper.toEntity(dto, student, internship);

                return InternshipApplicationMapper.toResponseDTO(
                                internshipApplicationRepository.save(entity));
        }

        public InternshipApplicationResponseDTO update(Long id, InternshipApplicationRequestDTO dto) {
                InternshipApplication existing = internshipApplicationRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException(
                                                "InternshipApplication not found with id: " + id));
                Student student = studentRepository.findById(dto.getStudentId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Student not found with id: " + dto.getStudentId()));
                Internship internship = internshipRepository.findById(dto.getInternshipId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Internship not found with id: " + dto.getInternshipId()));

                InternshipApplicationMapper.updateEntity(existing, dto);
                existing.setStudent(student);
                existing.setInternship(internship);

                return InternshipApplicationMapper.toResponseDTO(internshipApplicationRepository.save(existing));
        }

        public void deleteById(Long id) {
                internshipApplicationRepository.deleteById(id);
        }
}
