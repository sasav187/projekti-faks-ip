package com.example.internship.repository;

import com.example.internship.model.Skill;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    
    Page<Skill> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
