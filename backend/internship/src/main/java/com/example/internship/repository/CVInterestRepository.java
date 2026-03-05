package com.example.internship.repository;

import com.example.internship.model.CVInterest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

public interface CVInterestRepository extends JpaRepository<CVInterest, Long> {
    
    Page<CVInterest> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
