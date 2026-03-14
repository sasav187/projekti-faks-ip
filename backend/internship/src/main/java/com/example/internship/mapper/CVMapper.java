package com.example.internship.mapper;

import com.example.internship.dto.cv.CVRequestDTO;
import com.example.internship.dto.cv.CVResponseDTO;
import com.example.internship.model.CV;
import com.example.internship.model.Student;
import org.springframework.stereotype.Component;

@Component
public class CVMapper {

    public CVResponseDTO toDTO(CV cv) {
        if (cv == null) {
            return null;
        }

        return CVResponseDTO.builder()
                .id(cv.getId())
                .studentId(cv.getStudent().getId())
                .firstName(cv.getFirstName())
                .lastName(cv.getLastName())
                .email(cv.getEmail())
                .phone(cv.getPhone())
                .address(cv.getAddress())
                .nationality(cv.getNationality())
                .dateOfBirth(cv.getDateOfBirth())
                .summary(cv.getSummary())
                .imagePath(cv.getImagePath())
                .createdAt(cv.getCreatedAt())
                .lastUpdated(cv.getLastUpdated())

                .skills(cv.getSkills() != null
                        ? cv.getSkills().stream().map(CVSkillMapper::toResponseDTO).toList()
                        : null)

                .interests(cv.getInterests() != null
                        ? cv.getInterests().stream().map(CVInterestMapper::toResponseDTO).toList()
                        : null)

                .educationList(cv.getEducationList() != null
                        ? cv.getEducationList().stream().map(EducationMapper::toResponseDTO).toList()
                        : null)

                .workExperiences(cv.getWorkExperiences() != null
                        ? cv.getWorkExperiences().stream().map(WorkExperienceMapper::toResponseDTO).toList()
                        : null)

                .languages(cv.getLanguages() != null
                        ? cv.getLanguages().stream().map(LanguageMapper::toResponseDTO).toList()
                        : null)

                .additionalInfos(cv.getAdditionalInfos() != null
                        ? cv.getAdditionalInfos().stream().map(AdditionalInfoMapper::toResponseDTO).toList()
                        : null)

                .build();
    }


    public CV toEntity(CVRequestDTO dto, Student student) {

        if (dto == null) {
            return null;
        }

        CV cv = CV.builder()
                .student(student)
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .nationality(dto.getNationality())
                .dateOfBirth(dto.getDateOfBirth())
                .summary(dto.getSummary())
                .imagePath(dto.getImagePath())
                .build();

        if (dto.getSkills() != null) {
                cv.setSkills(
                        dto.getSkills().stream()
                                .map(skillDTO -> {
                                        var skill = CVSkillMapper.toEntity(skillDTO);
                                        skill.setCv(cv);
                                        return skill;
                                }).toList()
                );
        }

        if (dto.getInterests() != null) {
                cv.setInterests(
                        dto.getInterests().stream()
                                .map(interestDTO -> {
                                        var interest = CVInterestMapper.toEntity(interestDTO);
                                        interest.setCv(cv);
                                        return interest;
                                }).toList()
                );
        }

        if (dto.getEducationList() != null) {
                cv.setEducationList(
                        dto.getEducationList().stream()
                                .map(educationListDTO -> {
                                        var educationList = EducationMapper.toEntity(educationListDTO);
                                        educationList.setCv(cv);
                                        return educationList;
                                }).toList()
                );
        }

        if (dto.getWorkExperiences() != null) {
                cv.setWorkExperiences(
                        dto.getWorkExperiences().stream()
                                .map(workExperienceDTO -> {
                                        var workExperience = WorkExperienceMapper.toEntity(workExperienceDTO);
                                        workExperience.setCv(cv);
                                        return workExperience;
                                }).toList()
                );
        }

        if (dto.getLanguages() != null) {
                cv.setLanguages(
                        dto.getLanguages().stream()
                                .map(languageDTO -> {
                                        var language = LanguageMapper.toEntity(languageDTO);
                                        language.setCv(cv);
                                        return language;
                                }).toList()
                );
        }

        if (dto.getAdditionalInfos() != null) {
                cv.setAdditionalInfos(
                        dto.getAdditionalInfos().stream()
                                .map(additionalInfoDTO -> {
                                        var additionalInfo = AdditionalInfoMapper.toEntity(additionalInfoDTO);
                                        additionalInfo.setCv(cv);
                                        return additionalInfo;
                                }).toList()
                );
        }

        return cv;
    }

}