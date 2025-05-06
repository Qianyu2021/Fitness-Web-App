package com.fitness.AI_Service.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {
    private final WebClient webClient;
    @Value("${gemini.api.key}")
    private String geminiApiKey;
    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @PostConstruct
    public void init() {
        geminiApiKey = geminiApiKey.trim();
        geminiApiUrl = geminiApiUrl.trim();
    }

    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .build();
    }

    public String getAnswer(String question) {
        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(Map.of("text", question)))
                )
        );

        try {
            return webClient.post()
                    .uri(geminiApiUrl + "?key=" + geminiApiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            System.err.println("Gemini API Error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            throw e;  // or return a fallback response
        }
    }
}

