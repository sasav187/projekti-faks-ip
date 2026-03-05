package com.example.internship.repository;

import com.example.internship.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    List<Language> findAllByCvId(Long cvId);
}