package com.example.internship.repository;

import com.example.internship.model.Technology;

import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {
    
    Page<Technology> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Optional<Technology> findByNameIgnoreCase(String name);
}
