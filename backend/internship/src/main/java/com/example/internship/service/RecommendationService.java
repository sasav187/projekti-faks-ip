package com.example.internship.service;

import com.example.internship.dto.recommendation.*;
import com.example.internship.mapper.RecommendationMapper;
import com.example.internship.model.Recommendation;
import com.example.internship.model.Student;
import com.example.internship.model.Internship;
import com.example.internship.repository.RecommendationRepository;
import com.example.internship.repository.StudentRepository;
import com.example.internship.repository.InternshipRepository;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

        private final RecommendationRepository recommendationRepository;
        private final StudentRepository studentRepository;
        private final InternshipRepository internshipRepository;
        private final GroqService groqService;

        public RecommendationService(RecommendationRepository recommendationRepository,
                        StudentRepository studentRepository,
                        InternshipRepository internshipRepository,
                        GroqService groqService) {
                this.recommendationRepository = recommendationRepository;
                this.studentRepository = studentRepository;
                this.internshipRepository = internshipRepository;
                this.groqService = groqService;
        }

        public Page<RecommendationResponseDTO> getAllRecommendations(Pageable pageable) {
                return recommendationRepository.findAll(pageable)
                                .map(RecommendationMapper::toResponseDTO);
        }

        public Page<RecommendationResponseDTO> searchByExplanation(String explanation, Pageable pageable) {
                return recommendationRepository.findByExplanationContainingIgnoreCase(explanation, pageable)
                                .map(RecommendationMapper::toResponseDTO);
        }

        public RecommendationResponseDTO getById(Long id) {
                Recommendation rec = recommendationRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Recommendation not found with id: " + id));
                return RecommendationMapper.toResponseDTO(rec);
        }

        public RecommendationResponseDTO create(RecommendationRequestDTO dto) {
                Student student = studentRepository.findById(dto.getStudentId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Student not found with id: " + dto.getStudentId()));
                Internship internship = internshipRepository.findById(dto.getInternshipId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Internship not found with id: " + dto.getInternshipId()));
                Recommendation entity = RecommendationMapper.toEntity(dto, student, internship);
                return RecommendationMapper.toResponseDTO(recommendationRepository.save(entity));
        }

        public RecommendationResponseDTO update(Long id, RecommendationRequestDTO dto) {
                Recommendation existing = recommendationRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Recommendation not found with id: " + id));

                Student student = studentRepository.findById(dto.getStudentId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Student not found with id: " + dto.getStudentId()));
                Internship internship = internshipRepository.findById(dto.getInternshipId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Internship not found with id: " + dto.getInternshipId()));

                RecommendationMapper.updateEntity(existing, dto);
                existing.setStudent(student);
                existing.setInternship(internship);

                return RecommendationMapper.toResponseDTO(recommendationRepository.save(existing));
        }

        public void deleteById(Long id) {
                recommendationRepository.deleteById(id);
        }

        public RecommendationResponseDTO generateRecommendation(String username, Long internshipId) {

                Student student = studentRepository.findByUserUsername(username)
                                .orElseThrow(() -> new RuntimeException("Student not found"));

                Internship internship = internshipRepository.findById(internshipId)
                                .orElseThrow(() -> new RuntimeException("Internship not found"));

                if (student.getCv() == null) {
                        throw new RuntimeException("Student does not have CV");
                }

                String skills = student.getCv().getSkills().stream()
                                .map(s -> s.getName())
                                .collect(Collectors.joining(", "));

                String studentInfo = "Username: " + student.getUser().getUsername() + "\nSkills: " + skills;
                String internshipInfo = "Title: " + internship.getTitle() + "\nDescription: "
                                + internship.getDescription();

                String aiResponse;
                try {
                        aiResponse = groqService.generateRecommendationText(studentInfo, internshipInfo);
                } catch (Exception e) {
                        aiResponse = "Basic recommendation based on student's skills and internship description.";
                }

                Recommendation rec = new Recommendation();
                rec.setStudent(student);
                rec.setInternship(internship);
                rec.setExplanation(aiResponse);

                return RecommendationMapper.toResponseDTO(recommendationRepository.save(rec));
        }

        public List<RecommendationResponseDTO> generateRecommendationsForStudent(String username) {

                Student student = studentRepository.findByUserUsername(username)
                                .orElseThrow(() -> new RuntimeException("Student not found"));

                if (student.getCv() == null) {
                        throw new RuntimeException("Student does not have CV");
                }

                List<Internship> internships = internshipRepository.findAll();

                List<Recommendation> results = new ArrayList<>();

                for (Internship internship : internships) {

                        Recommendation rec = recommendationRepository
                                        .findByStudentAndInternship(student, internship)
                                        .orElse(null);

                        if (rec == null) {

                                String skills = student.getCv().getSkills().stream()
                                                .map(s -> s.getName())
                                                .collect(Collectors.joining(", "));

                                String studentInfo = "Username: " + student.getUser().getUsername() + "\nSkills: "
                                                + skills;

                                String internshipInfo = "Title: " + internship.getTitle()
                                                + "\nDescription: " + internship.getDescription();

                                String aiResponse;
                                double score;

                                try {
                                        aiResponse = groqService.generateRecommendationText(studentInfo,
                                                        internshipInfo);

                                        score = extractScore(aiResponse);

                                } catch (Exception e) {
                                        aiResponse = "Basic recommendation based on student's skills.";
                                        score = Math.random();
                                }

                                rec = new Recommendation();
                                rec.setStudent(student);
                                rec.setInternship(internship);
                                rec.setExplanation(aiResponse);
                                rec.setScore(score);

                                rec = recommendationRepository.save(rec);
                        }

                        results.add(rec);
                }

                return results.stream()
                                .sorted((a, b) -> Double.compare(b.getScore(), a.getScore()))
                                .limit(5)
                                .map(RecommendationMapper::toResponseDTO)
                                .toList();
        }

        private double extractScore(String text) {
                try {
                        String number = text.replaceAll("[^0-9.]", "");
                        return Double.parseDouble(number);
                } catch (Exception e) {
                        return Math.random();
                }
        }
}