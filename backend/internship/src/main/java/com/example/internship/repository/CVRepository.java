package com.example.internship.repository;

import com.example.internship.model.CV;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CVRepository extends JpaRepository<CV, Long> {
}
