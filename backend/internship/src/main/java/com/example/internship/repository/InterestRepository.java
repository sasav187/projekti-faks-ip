package com.example.internship.repository;

import com.example.internship.model.Interest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

public interface InterestRepository extends JpaRepository<Interest, Long> {
    
    Page<Interest> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
