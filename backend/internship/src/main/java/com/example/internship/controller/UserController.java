package com.example.internship.controller;

import com.example.internship.model.User;
import com.example.internship.service.UserService;
import com.example.internship.dto.UserDTO;
import com.example.internship.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<UserDTO> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return userService.getAllUsers(pageable)
                .map(UserMapper::toDTO);
    }

    @GetMapping("/search")
    public Page<UserDTO> searchByUsername(
            @RequestParam String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return userService.searchByUsername(username, pageable)
                .map(UserMapper::toDTO);
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return UserMapper.toDTO(user);
    }

    @PostMapping
    public UserDTO create(@RequestBody User user) {
        User saved = userService.save(user);
        return UserMapper.toDTO(saved);
    }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        User updated = userService.save(user);
        return UserMapper.toDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
