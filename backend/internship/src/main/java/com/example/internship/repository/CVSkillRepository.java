package com.example.internship.repository;

import com.example.internship.model.CVSkill;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

public interface CVSkillRepository extends JpaRepository<CVSkill, Long> {
    
    Page<CVSkill> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
