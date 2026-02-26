package com.example.internship.dto.company;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRequestDTO {
    
    private Long userId;
    private String name;
    private String description;
    private Boolean approved;
}
