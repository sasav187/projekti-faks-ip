package com.example.internship.controller;

import com.example.internship.model.WorkLog;
import com.example.internship.repository.WorkLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/worklogs")
@RequiredArgsConstructor
public class WorkLogController {

    private final WorkLogRepository repository;

    @GetMapping
    public ResponseEntity<List<WorkLog>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkLog> getById(@PathVariable Long id) {
        Optional<WorkLog> s = repository.findById(id);
        return s.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<WorkLog> create(@RequestBody WorkLog entity) {
        WorkLog saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkLog> update(@PathVariable Long id, @RequestBody WorkLog entity) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setStudent(entity.getStudent());
                    existing.setInternship(entity.getInternship());
                    existing.setWeekNumber(entity.getWeekNumber());
                    existing.setDescription(entity.getDescription());
                    WorkLog saved = repository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
