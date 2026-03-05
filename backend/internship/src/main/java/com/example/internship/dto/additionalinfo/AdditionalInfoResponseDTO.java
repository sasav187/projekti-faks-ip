package com.example.internship.dto.additionalinfo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdditionalInfoResponseDTO {
    
    private Long id;

    private Long cvId;

    private String content;
}
