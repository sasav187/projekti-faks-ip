package com.example.internship.repository;

import com.example.internship.model.Student;

import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

public interface StudentRepository extends JpaRepository<Student, Long> {
    
    Page<Student> findByIndexNumberContainingIgnoreCase(String indexNumber, Pageable pageable);
    Optional<Student> findByUserUsername(String username);
}
