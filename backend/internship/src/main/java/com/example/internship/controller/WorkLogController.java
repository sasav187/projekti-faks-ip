package com.example.internship.controller;

import com.example.internship.model.WorkLog;
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
    public Page<WorkLog> getAllWorkLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return workLogService.getAllWorkLogs(pageable);
    }

    @GetMapping("/search")
    public Page<WorkLog> searchByDescription(
            @RequestParam String description,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return workLogService.searchByDescription(description, pageable);
    }

    @GetMapping("/{id}")
    public WorkLog getById(@PathVariable Long id) {
        return workLogService.getById(id);
    }

    @PostMapping
    public WorkLog create(@RequestBody WorkLog workLog) {
        return workLogService.save(workLog);
    }

    @PutMapping("/{id}")
    public WorkLog update(@PathVariable Long id, @RequestBody WorkLog workLog) {
        workLog.setId(id);
        return workLogService.save(workLog);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        workLogService.deleteById(id);
    }
}
