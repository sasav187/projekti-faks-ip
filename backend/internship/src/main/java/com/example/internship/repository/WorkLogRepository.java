package com.example.internship.repository;

import com.example.internship.model.WorkLog;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {
    
    Page<WorkLog> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
}
