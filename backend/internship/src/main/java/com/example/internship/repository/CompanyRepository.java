package com.example.internship.repository;

import com.example.internship.model.Company;

import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    
    Page<Company> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Optional<Company> findByUserId(Long userId);
}
