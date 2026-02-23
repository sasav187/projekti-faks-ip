package com.example.internship.repository;

import com.example.internship.model.Evaluation;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    
    Page<Evaluation> findByStudentIndexNumberContainingIgnoreCase(String indexNumber, Pageable pageable);
}
