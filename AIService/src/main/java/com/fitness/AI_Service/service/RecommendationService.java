package com.fitness.AI_Service.service;

import com.fitness.AI_Service.model.Recommendation;
import com.fitness.AI_Service.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;


    public List<Recommendation> getAllRecommendations(String userId) {
        // Assuming you have a userId field in your Recommendation model
        return recommendationRepository.findAllByUserId(userId);
    }

    public Recommendation getRecommendationsByActivityId(String activityId) {
        // Assuming you have an activityId field in your Recommendation model
        return recommendationRepository.findAllByActivityId(activityId)
                .orElseThrow(() -> new RuntimeException("No recommendations found for activity ID: " + activityId));
    }
}
