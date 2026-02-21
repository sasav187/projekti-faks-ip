package com.example.internship.controller;

import com.example.internship.model.InternshipApplication;
import com.example.internship.repository.InternshipApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class InternshipApplicationController {

    private final InternshipApplicationRepository repository;

    @GetMapping
    public ResponseEntity<List<InternshipApplication>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InternshipApplication> getById(@PathVariable Long id) {
        Optional<InternshipApplication> s = repository.findById(id);
        return s.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InternshipApplication> create(@RequestBody InternshipApplication entity) {
        InternshipApplication saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InternshipApplication> update(@PathVariable Long id, @RequestBody InternshipApplication entity) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        entity.setId(id);
        InternshipApplication saved = repository.save(entity);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
