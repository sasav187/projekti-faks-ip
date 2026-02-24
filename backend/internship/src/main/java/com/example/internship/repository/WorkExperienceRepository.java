package com.example.internship.repository;

import com.example.internship.model.WorkExperience;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {
    
    Page<WorkExperience> findByCompanyNameContainingIgnoreCase(String companyName, Pageable pageable);
}
