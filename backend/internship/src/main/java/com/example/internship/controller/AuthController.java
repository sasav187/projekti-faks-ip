package com.example.internship.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.example.internship.dto.login.LoginRequest;
import com.example.internship.dto.login.LoginResponse;
import com.example.internship.model.User;
import com.example.internship.repository.UserRepository;
import com.example.internship.repository.CompanyRepository;
import com.example.internship.repository.StudentRepository;
import com.example.internship.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

        private final AuthenticationManager authenticationManager;
        private final JwtUtil jwtUtil;
        private final UserRepository userRepository;
        private final CompanyRepository companyRepository;
        private final StudentRepository studentRepository;

        public AuthController(
                        AuthenticationManager authenticationManager,
                        JwtUtil jwtUtil,
                        UserRepository userRepository,
                        CompanyRepository companyRepository,
                        StudentRepository studentRepository) {
                this.authenticationManager = authenticationManager;
                this.jwtUtil = jwtUtil;
                this.userRepository = userRepository;
                this.companyRepository = companyRepository;
                this.studentRepository = studentRepository;
        }

        @PostMapping("/login")
        public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

                User user = userRepository.findByUsername(request.getUsername())
                                .orElseThrow(() -> new RuntimeException("User not found"));

                String token;

                if ("COMPANY".equals(user.getRole().name())) {

                        var company = companyRepository.findByUserId(user.getId())
                                        .orElseThrow(() -> new RuntimeException("Company not found"));

                        if (!company.getApproved()) {
                                return ResponseEntity.status(403)
                                                .body(new LoginResponse(null, "NOT_APPROVED"));
                        }

                        token = jwtUtil.generateToken(
                                        user.getUsername(),
                                        user.getRole().name(),
                                        user.getId(),
                                        company.getId(),
                                        null);

                } else {

                        var student = studentRepository.findByUserId(user.getId()).orElse(null);

                        Long studentId = student != null ? student.getId() : null;

                        token = jwtUtil.generateToken(
                                        user.getUsername(),
                                        user.getRole().name(),
                                        user.getId(),
                                        null,
                                        studentId);
                }

                return ResponseEntity.ok(new LoginResponse(token, user.getRole().name()));
        }
}