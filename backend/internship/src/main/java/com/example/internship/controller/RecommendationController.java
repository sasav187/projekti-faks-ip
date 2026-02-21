package com.example.internship.controller;

import com.example.internship.model.Recommendation;
import com.example.internship.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationRepository repository;

    @GetMapping
    public ResponseEntity<List<Recommendation>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recommendation> getById(@PathVariable Long id) {
        Optional<Recommendation> s = repository.findById(id);
        return s.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Recommendation> create(@RequestBody Recommendation entity) {
        Recommendation saved = repository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recommendation> update(@PathVariable Long id, @RequestBody Recommendation entity) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setStudent(entity.getStudent());
                    existing.setInternship(entity.getInternship());
                    existing.setScore(entity.getScore());
                    existing.setExplanation(entity.getExplanation());
                    Recommendation saved = repository.save(existing);
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
