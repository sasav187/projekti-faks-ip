package com.example.internship.repository;

import com.example.internship.model.Education;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

public interface EducationRepository extends JpaRepository<Education, Long> {
    
    Page<Education> findByInstitutionContainingIgnoreCase(String institution, Pageable pageable);
}
