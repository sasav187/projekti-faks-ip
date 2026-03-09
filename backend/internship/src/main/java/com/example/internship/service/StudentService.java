package com.example.internship.service;

import com.example.internship.dto.student.*;
import com.example.internship.mapper.StudentMapper;
import com.example.internship.model.Student;
import com.example.internship.model.User;
import com.example.internship.repository.StudentRepository;
import com.example.internship.repository.UserRepository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public StudentService(StudentRepository studentRepository,
                          UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    public Page<StudentResponseDTO> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable)
                .map(StudentMapper::toResponseDTO);
    }

    public Page<StudentResponseDTO> searchByIndexNumber(String indexNumber, Pageable pageable) {
        return studentRepository.findByIndexNumberContainingIgnoreCase(indexNumber, pageable)
                .map(StudentMapper::toResponseDTO);
    }

    public Student getByUserName(String username) {
        return studentRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Student not found with username: " + username));
    }

    public StudentResponseDTO getById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        return StudentMapper.toResponseDTO(student);
    }

    public StudentResponseDTO create(StudentRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
        Student entity = StudentMapper.toEntity(dto, user);
        return StudentMapper.toResponseDTO(studentRepository.save(entity));
    }

    public StudentResponseDTO update(Long id, StudentRequestDTO dto) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
        StudentMapper.updateEntity(existing, dto);
        existing.setUser(user);
        return StudentMapper.toResponseDTO(studentRepository.save(existing));
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }
}
