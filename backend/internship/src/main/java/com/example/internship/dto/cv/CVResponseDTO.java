package com.example.internship.dto.cv;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.internship.dto.cvskill.CVSkillResponseDTO;
import com.example.internship.dto.cvinterest.CVInterestResponseDTO;
import com.example.internship.dto.education.EducationResponseDTO;
import com.example.internship.dto.language.LanguageResponseDTO;
import com.example.internship.dto.additionalinfo.AdditionalInfoResponseDTO;
import com.example.internship.dto.workexperience.WorkExperienceResponseDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CVResponseDTO {

    private Long id;

    private Long studentId;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String address;

    private String nationality;

    private LocalDate dateOfBirth;

    private String summary;

    private String imagePath;

    private LocalDateTime createdAt;
    
    private LocalDateTime lastUpdated;

    private List<CVSkillResponseDTO> skills;
    private List<CVInterestResponseDTO> interests;
    private List<EducationResponseDTO> educationList;
    private List<WorkExperienceResponseDTO> workExperiences;
    private List<LanguageResponseDTO> languages;
    private List<AdditionalInfoResponseDTO> additionalInfos;
}