package com.example.internship.repository;

import com.example.internship.model.Technology;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {
    
    Page<Technology> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
