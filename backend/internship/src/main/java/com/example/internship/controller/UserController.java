package com.example.internship.controller;

import com.example.internship.model.User;
import com.example.internship.service.UserService;
import com.example.internship.dto.user.UserRequestDTO;
import com.example.internship.dto.user.UserResponseDTO;
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
    public Page<UserResponseDTO> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return userService.getAllUsers(pageable)
                .map(UserMapper::toResponseDTO);
    }

    @GetMapping("/search")
    public Page<UserResponseDTO> searchByUsername(
            @RequestParam String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return userService.searchByUsername(username, pageable)
                .map(UserMapper::toResponseDTO);
    }

    @GetMapping("/{id}")
    public UserResponseDTO getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return UserMapper.toResponseDTO(user);
    }

    @PostMapping
    public UserResponseDTO create(@RequestBody UserRequestDTO userDto) {
        User user = UserMapper.toEntity(userDto);
        User saved = userService.save(user);
        return UserMapper.toResponseDTO(saved);
    }

    @PutMapping("/{id}")
    public UserResponseDTO update(@PathVariable Long id,
                                  @RequestBody UserRequestDTO userDto) {

        User existing = userService.getById(id);
        UserMapper.updateEntity(existing, userDto);
        User updated = userService.save(existing);
        return UserMapper.toResponseDTO(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
