package com.example.internship.repository;

import com.example.internship.model.User;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    Page<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
}
