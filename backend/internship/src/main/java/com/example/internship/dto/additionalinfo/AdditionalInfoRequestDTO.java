package com.example.internship.dto.additionalinfo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdditionalInfoRequestDTO {
    
    private Long cvId;

    private String content;
}
