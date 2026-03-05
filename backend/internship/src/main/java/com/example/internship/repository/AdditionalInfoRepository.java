package com.example.internship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.internship.model.AdditionalInfo;

import java.util.List;

public interface AdditionalInfoRepository extends JpaRepository<AdditionalInfo, Long> {
    List<AdditionalInfo> findAllByCvId(Long cvId);
}