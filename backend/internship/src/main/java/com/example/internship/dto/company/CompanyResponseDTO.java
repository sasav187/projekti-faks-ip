package com.example.internship.dto.company;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyResponseDTO {
    
    private Long id;
    private Long userId;
    private String username;
    private String name;
    private String description;
    private Boolean approved;
}
