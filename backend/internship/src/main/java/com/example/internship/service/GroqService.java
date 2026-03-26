package com.example.internship.service;

import com.example.internship.dto.groq.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class GroqService {

    private final RestClient client = RestClient.create("https://api.groq.com/openai/v1");

    @Value("${groq.api.key}")
    private String apiKey;

    public String generateRecommendationText(String studentInfo, String internshipInfo) {

        String prompt = """
                Based on the following student and internship data, generate a short recommendation:

                Student:
                %s

                Internship:
                %s

                Give a clear explanation why this internship suits the student.
                """.formatted(studentInfo, internshipInfo);

        GroqResponseDTO response = client.post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .body("""
                        {
                          "model": "llama-3.3-70b-versatile",
                          "messages": [
                            {"role": "user", "content": "%s"}
                          ]
                        }
                        """.formatted(prompt))
                .retrieve()
                .body(GroqResponseDTO.class);

        return response.getChoices().get(0).getMessage().getContent();
    }
}