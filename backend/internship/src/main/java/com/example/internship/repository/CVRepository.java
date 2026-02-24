package com.example.internship.repository;

import com.example.internship.model.CV;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

public interface CVRepository extends JpaRepository<CV, Long> {
    
    Page<CV> findBySummaryContainingIgnoreCase(String summary, Pageable pageable);
}
