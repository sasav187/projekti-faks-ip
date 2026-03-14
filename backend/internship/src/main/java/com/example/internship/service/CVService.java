package com.example.internship.service;

import com.example.internship.dto.cv.*;
import com.example.internship.mapper.AdditionalInfoMapper;
import com.example.internship.mapper.CVInterestMapper;
import com.example.internship.mapper.CVMapper;
import com.example.internship.mapper.CVSkillMapper;
import com.example.internship.mapper.EducationMapper;
import com.example.internship.mapper.LanguageMapper;
import com.example.internship.mapper.WorkExperienceMapper;
import com.example.internship.model.CV;
import com.example.internship.model.Student;
import com.example.internship.repository.CVRepository;
import com.example.internship.repository.StudentRepository;

import java.nio.file.*;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CVService {

        private final CVRepository cvRepository;
        private final StudentRepository studentRepository;
        private final CVMapper cvMapper;

        public CVService(CVRepository cvRepository,
                        StudentRepository studentRepository,
                        CVMapper cvMapper) {
                this.cvRepository = cvRepository;
                this.studentRepository = studentRepository;
                this.cvMapper = cvMapper;
        }

        public Page<CVResponseDTO> getAllCVs(Pageable pageable) {
                return cvRepository.findAll(pageable)
                                .map(cvMapper::toDTO);
        }

        public Page<CVResponseDTO> searchBySummary(String summary, Pageable pageable) {
                return cvRepository.findBySummaryContainingIgnoreCase(summary, pageable)
                                .map(cvMapper::toDTO);
        }

        public CVResponseDTO getById(Long id) {
                CV cv = cvRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("CV not found with id: " + id));

                return cvMapper.toDTO(cv);
        }

        public CVResponseDTO getCurrentStudentCV(Long studentId) {
                Student student = studentRepository.findById(studentId)
                                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

                CV cv = cvRepository.findByStudentId(studentId)
                                .orElseGet(() -> {
                                        CV newCv = CV.builder()
                                                        .student(student)
                                                        .build();
                                        return cvRepository.save(newCv);
                                });

                return cvMapper.toDTO(cv);
        }

        public CVResponseDTO updateCurrentStudentCV(Long studentId, CVRequestDTO dto) {
                CV cv = cvRepository.findByStudentId(studentId)
                                .orElseThrow(() -> new RuntimeException("CV not found for student id: " + studentId));

                cv.setFirstName(dto.getFirstName());
                cv.setLastName(dto.getLastName());
                cv.setEmail(dto.getEmail());
                cv.setPhone(dto.getPhone());
                cv.setAddress(dto.getAddress());
                cv.setNationality(dto.getNationality());
                cv.setDateOfBirth(dto.getDateOfBirth());
                cv.setSummary(dto.getSummary());
                cv.setImagePath(dto.getImagePath());

                return cvMapper.toDTO(cvRepository.save(cv));
        }

        public CVResponseDTO create(CVRequestDTO dto) {

                Student student = studentRepository.findById(dto.getStudentId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Student not found with id: " + dto.getStudentId()));

                CV entity = cvMapper.toEntity(dto, student);

                return cvMapper.toDTO(
                                cvRepository.save(entity));
        }

        public CVResponseDTO uploadImage(Long id, MultipartFile file) {

                CV cv = cvRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("CV not found"));

                try {

                        String uploadDir = "uploads/cv-images/";
                        Files.createDirectories(Paths.get(uploadDir));
                        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                        Path path = Paths.get(uploadDir + fileName);

                        Files.write(path, file.getBytes());

                        cv.setImagePath("/uploads/cv-images/" + fileName);
                        cvRepository.save(cv);

                        return cvMapper.toDTO(cv);

                } catch (Exception e) {
                        throw new RuntimeException("Image upload failed");
                }
        }

        public CVResponseDTO update(Long id, CVRequestDTO dto) {

                CV existing = cvRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("CV not found with id: " + id));

                existing.setFirstName(dto.getFirstName());
                existing.setLastName(dto.getLastName());
                existing.setEmail(dto.getEmail());
                existing.setPhone(dto.getPhone());
                existing.setAddress(dto.getAddress());
                existing.setNationality(dto.getNationality());
                existing.setDateOfBirth(dto.getDateOfBirth());
                existing.setSummary(dto.getSummary());
                existing.setImagePath(dto.getImagePath());

                if (dto.getSkills() != null) {
                        existing.getSkills().clear();
                        dto.getSkills().forEach(skillDTO -> {
                                var skill = CVSkillMapper.toEntity(skillDTO);
                                skill.setCv(existing);
                                existing.getSkills().add(skill);
                        });
                }

                if (dto.getInterests() != null) {
                        existing.getInterests().clear();
                        dto.getInterests().forEach(interestDTO -> {
                                var interest = CVInterestMapper.toEntity(interestDTO);
                                interest.setCv(existing);
                                existing.getInterests().add(interest);
                        });
                }

                if (dto.getEducationList() != null) {
                        existing.getEducationList().clear();
                        dto.getEducationList().forEach(educationListDTO -> {
                                var educationList = EducationMapper.toEntity(educationListDTO);
                                educationList.setCv(existing);
                                existing.getEducationList().add(educationList);
                        });
                }

                if (dto.getWorkExperiences() != null) {
                        existing.getWorkExperiences().clear();
                        dto.getWorkExperiences().forEach(workExperienceDTO -> {
                                var workExperience = WorkExperienceMapper.toEntity(workExperienceDTO);
                                workExperience.setCv(existing);
                                existing.getWorkExperiences().add(workExperience);
                        });
                }

                if (dto.getLanguages() != null) {
                        existing.getLanguages().clear();
                        dto.getLanguages().forEach(languageDTO -> {
                                var language = LanguageMapper.toEntity(languageDTO);
                                language.setCv(existing);
                                existing.getLanguages().add(language);
                        });
                }

                if (dto.getAdditionalInfos() != null) {
                        existing.getAdditionalInfos().clear();
                        dto.getAdditionalInfos().forEach(additionalInfoDTO -> {
                                var additionalInfo = AdditionalInfoMapper.toEntity(additionalInfoDTO);
                                additionalInfo.setCv(existing);
                                existing.getAdditionalInfos().add(additionalInfo);
                        });
                }

                return cvMapper.toDTO(
                                cvRepository.save(existing));
        }

        public void deleteById(Long id) {
                cvRepository.deleteById(id);
        }
}