package com.example.internship.repository;

import com.example.internship.model.Internship;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

public interface InternshipRepository extends JpaRepository<Internship, Long> {
    
    Page<Internship> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Internship> findByCompany_NameContainingIgnoreCase(String company, Pageable pageable);
    Page<Internship> findByTechnologies_NameContainingIgnoreCase(String technology, Pageable pageable);
    Page<Internship> findByTitleContainingIgnoreCaseAndTechnologies_NameContainingIgnoreCase(
        String title,
        String technology,
        Pageable pageable);
}
