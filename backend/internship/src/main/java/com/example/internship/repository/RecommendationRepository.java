package com.example.internship.repository;

import com.example.internship.model.Internship;
import com.example.internship.model.Recommendation;
import com.example.internship.model.Student;

import java.util.Optional;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    Page<Recommendation> findByExplanationContainingIgnoreCase(String explanation, Pageable pageable);

    Optional<Recommendation> findByStudentAndInternship(Student student, Internship internship);
}
