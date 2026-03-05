package com.example.internship.dto.cvinterest;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CVInterestResponseDTO {

    private Long id;

    private Long cvId;

    private String name;
}
