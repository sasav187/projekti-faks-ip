package com.example.internship.controller;

import com.example.internship.dto.additionalinfo.*;
import com.example.internship.service.AdditionalInfoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/additional-info")
public class AdditionalInfoController {

    private final AdditionalInfoService service;

    public AdditionalInfoController(AdditionalInfoService service) {
        this.service = service;
    }

    @PostMapping
    public AdditionalInfoResponseDTO create(@RequestBody AdditionalInfoRequestDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public AdditionalInfoResponseDTO update(@PathVariable Long id, @RequestBody AdditionalInfoRequestDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/cv/{cvId}")
    public List<AdditionalInfoResponseDTO> getAllByCv(@PathVariable Long cvId) {
        return service.getAllByCv(cvId);
    }
}