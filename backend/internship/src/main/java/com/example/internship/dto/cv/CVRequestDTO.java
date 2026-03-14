package com.example.internship.dto.cv;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

import com.example.internship.dto.additionalinfo.AdditionalInfoRequestDTO;
import com.example.internship.dto.cvinterest.CVInterestRequestDTO;
import com.example.internship.dto.cvskill.CVSkillRequestDTO;
import com.example.internship.dto.education.EducationRequestDTO;
import com.example.internship.dto.language.LanguageRequestDTO;
import com.example.internship.dto.workexperience.WorkExperienceRequestDTO;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CVRequestDTO {

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

    private List<CVSkillRequestDTO> skills;
    private List<CVInterestRequestDTO> interests;
    private List<EducationRequestDTO> educationList;
    private List<WorkExperienceRequestDTO> workExperiences;
    private List<LanguageRequestDTO> languages;
    private List<AdditionalInfoRequestDTO> additionalInfos;
}