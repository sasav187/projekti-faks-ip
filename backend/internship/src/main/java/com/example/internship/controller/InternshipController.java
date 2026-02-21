package com.example.internship.controller;

import com.example.internship.model.Internship;
import com.example.internship.repository.InternshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/internships")
@RequiredArgsConstructor
public class InternshipController {

    private final InternshipRepository repository;

    @GetMapping
    public ResponseEntity<List<Internship>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Internship> getById(@PathVariable Long id) {
        Optional<Internship> s = repository.findById(id);
        return s.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Internship> create(@RequestBody Internship internship) {
        Internship saved = repository.save(internship);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Internship> update(@PathVariable Long id, @RequestBody Internship internship) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        internship.setId(id);
        Internship saved = repository.save(internship);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
