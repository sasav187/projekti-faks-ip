package com.example.internship.dto.cvinterest;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CVInterestRequestDTO {

    private Long cvId;

    private String name;
}
