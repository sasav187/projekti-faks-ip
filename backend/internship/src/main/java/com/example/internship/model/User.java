package com.example.internship.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.example.internship.model.enums.Role;

@Entity
@Table(name = "users")
@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private Boolean active;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "changed_password_at")
    private LocalDateTime changedPasswordAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        
        if (active == null) {
            active = true;
        }
    }
}