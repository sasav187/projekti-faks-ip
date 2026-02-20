package com.example.internship;

import com.example.internship.model.User;
import com.example.internship.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    public TestDataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.findAll().forEach(user -> {
            System.out.println("User: " + user.getUsername() + ", Role: " + user.getRole());
        });
    }
}