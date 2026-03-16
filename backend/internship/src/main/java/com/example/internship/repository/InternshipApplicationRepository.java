package com.example.internship.repository;

import com.example.internship.model.InternshipApplication;
import com.example.internship.model.Student;

import java.util.List;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

public interface InternshipApplicationRepository extends JpaRepository<InternshipApplication, Long> {

    Page<InternshipApplication> findByApplicationMessageContainingIgnoreCase(
            String applicationMessage,
            Pageable pageable);
    
    List<InternshipApplication> findByStudent(Student student); 
}
