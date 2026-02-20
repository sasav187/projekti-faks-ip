package com.example.internship.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.internship.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
