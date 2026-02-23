package com.example.internship.repository;

import com.example.internship.model.User;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Page<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
}
