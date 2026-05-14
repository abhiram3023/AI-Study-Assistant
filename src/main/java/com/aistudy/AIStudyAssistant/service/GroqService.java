package com.aistudy.AIStudyAssistant.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GroqService {

    @Value("${groq.api.key}")
    private String apiKey;

    @Value("${groq.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getResponse(String question) {

        String url = "https://api.groq.com/openai/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.setBearerAuth(apiKey);

        Map<String, Object> body = Map.of(
                "model", model,

                "messages", List.of(
                        Map.of(
                                "role", "system",
                                "content",
                                "You are a smart AI study assistant helping students."
                        ),
                        Map.of(
                                "role", "user",
                                "content", question
                        )
                ),

                "temperature", 0.5
        );

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        try {

            ResponseEntity<String> response =
                    restTemplate.postForEntity(
                            url,
                            request,
                            String.class
                    );

            JsonNode root =
                    objectMapper.readTree(response.getBody());

            return root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

        } catch (HttpStatusCodeException e) {

            return "Groq API Error: " +
                    e.getResponseBodyAsString();

        } catch (Exception e) {

            return "Error calling Groq API: " +
                    e.getMessage();
        }
    }
}