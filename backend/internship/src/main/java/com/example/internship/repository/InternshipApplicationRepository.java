package com.example.internship.repository;

import com.example.internship.model.InternshipApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternshipApplicationRepository extends JpaRepository<InternshipApplication, Long> {
}
