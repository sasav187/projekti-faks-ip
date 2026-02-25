package com.example.internship.controller;

import com.example.internship.dto.worklog.WorkLogRequestDTO;
import com.example.internship.dto.worklog.WorkLogResponseDTO;
import com.example.internship.service.WorkLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/worklogs")
@CrossOrigin
public class WorkLogController {

    private final WorkLogService workLogService;

    @Autowired
    public WorkLogController(WorkLogService workLogService) {
        this.workLogService = workLogService;
    }

    @GetMapping
    public Page<WorkLogResponseDTO> getAllWorkLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return workLogService.getAllWorkLogs(pageable);
    }

    @GetMapping("/search")
    public Page<WorkLogResponseDTO> searchByDescription(
            @RequestParam String description,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return workLogService.searchByDescription(description, pageable);
    }

    @GetMapping("/{id}")
    public WorkLogResponseDTO getById(@PathVariable Long id) {
        return workLogService.getById(id);
    }

    @PostMapping
    public WorkLogResponseDTO create(@RequestBody WorkLogRequestDTO dto) {
        return workLogService.create(dto);
    }

    @PutMapping("/{id}")
    public WorkLogResponseDTO update(@PathVariable Long id,
                                     @RequestBody WorkLogRequestDTO dto) {
        return workLogService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        workLogService.deleteById(id);
    }
}