package com.example.internship.repository;

import com.example.internship.model.FacultyAdmin;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

public interface FacultyAdminRepository extends JpaRepository<FacultyAdmin, Long> {
    
    Page<FacultyAdmin> findByFacultyNameContainingIgnoreCase(String facultyName, Pageable pageable);
}
