package com.example.internship.repository;

import com.example.internship.model.Internship;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

public interface InternshipRepository extends JpaRepository<Internship, Long> {
    
    Page<Internship> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
