package com.example.internship.controller;

import com.example.internship.dto.student.*;
import com.example.internship.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@CrossOrigin
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public Page<StudentResponseDTO> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentService.getAllStudents(pageable);
    }

    @GetMapping("/search")
    public Page<StudentResponseDTO> searchByIndexNumber(
            @RequestParam String indexNumber,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentService.searchByIndexNumber(indexNumber, pageable);
    }

    @GetMapping("/{id}")
    public StudentResponseDTO getById(@PathVariable Long id) {
        return studentService.getById(id);
    }

    @PostMapping
    public StudentResponseDTO create(@RequestBody StudentRequestDTO dto) {
        return studentService.create(dto);
    }

    @PutMapping("/{id}")
    public StudentResponseDTO update(@PathVariable Long id, @RequestBody StudentRequestDTO dto) {
        return studentService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        studentService.deleteById(id);
    }
}
