package com.fitness.AI_Service.controller;


import com.fitness.AI_Service.model.Recommendation;
import com.fitness.AI_Service.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendation>> getAllRecommendations(@PathVariable String userId) {
        List<Recommendation> recommendations = recommendationService.getAllRecommendations(userId);
        if (recommendations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(recommendations);
    }

    @GetMapping("/activities/{activityId}")
    public ResponseEntity<Recommendation> getRecommendationsByActivityId(@PathVariable String activityId) {
        Recommendation recommendations = recommendationService.getRecommendationsByActivityId(activityId);
        return ResponseEntity.ok(recommendations);
    }
}
