package com.example.internship.repository;

import com.example.internship.model.Recommendation;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    
    Page<Recommendation> findByExplanationContainingIgnoreCase(String explanation, Pageable pageable);
}
