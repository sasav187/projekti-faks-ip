package com.example.internship.controller;

import com.example.internship.dto.cv.*;
import com.example.internship.model.Student;
import com.example.internship.service.CVService;
import com.example.internship.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/cvs")
@CrossOrigin
public class CVController {

    private final CVService cvService;
    private final StudentService studentService;

    @Autowired
    public CVController(CVService cvService, StudentService studentService) {
        this.cvService = cvService;
        this.studentService = studentService;
    }

    @GetMapping
    public Page<CVResponseDTO> getAllCVs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cvService.getAllCVs(pageable);
    }

    @GetMapping("/search")
    public Page<CVResponseDTO> searchBySummary(
            @RequestParam String summary,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cvService.searchBySummary(summary, pageable);
    }

    @GetMapping("/{id}")
    public CVResponseDTO getById(@PathVariable Long id) {
        return cvService.getById(id);
    }

    @GetMapping("/me")
    public CVResponseDTO getCurrentStudentCV(Authentication authentication) {
        String username = authentication.getName();
        
        Student student = studentService.getByUserName(username);
        
        return cvService.getCurrentStudentCV(student.getId());
    }

    @PostMapping
    public CVResponseDTO create(@RequestBody CVRequestDTO dto) {
        return cvService.create(dto);
    }

    @PutMapping("/{id}")
    public CVResponseDTO update(@PathVariable Long id,
                                @RequestBody CVRequestDTO dto) {
        return cvService.update(id, dto);
    }

    @PostMapping("/me")
    public CVResponseDTO updateCurrentStudentCV(Authentication authentication,
                                                @RequestBody CVRequestDTO dto) {
        
        String username = authentication.getName();
        Student student = studentService.getByUserName(username);
        
        return cvService.updateCurrentStudentCV(student.getId(), dto);
    }

    @PostMapping("/upload-image/{id}")
    public CVResponseDTO uploadImage(@PathVariable Long id,
                                     @RequestParam("file") MultipartFile file) {
        return cvService.uploadImage(id, file);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cvService.deleteById(id);
    }
}
