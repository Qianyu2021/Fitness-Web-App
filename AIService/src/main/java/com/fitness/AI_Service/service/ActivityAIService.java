package com.fitness.AI_Service.service;

import com.fitness.AI_Service.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAIService {
    private final GeminiService geminiService;

    public String generateRecommendation(Activity activity) {
        String prompt = generatePrompt(activity);
        log.info("Generated prompt for activity recommendation: {}", prompt);
        String response = geminiService.getAnswer(prompt);
        log.info("Received response from Gemini: {}", response);
        return response;
    }

    private String generatePrompt(Activity activity) {
        return String.format("""
        Based on the activity data, here is the analysis and recommendation in the JSON format:
        {
          "analysis": {
            "userId": "%s",
            "activityType": "%s",
            "duration": "%s",
            "caloriesBurned": %s,
            "additionalMetrics": %s,
            "createdAt": "%s",
            "updatedAt": "%s"
          },
          "recommendations": [
            {
              "suggestion": "Increase your activity duration by 10 minutes for better results.",
              "reason": "Longer duration can lead to more calories burned and improved fitness."
            }
          ]
        }
        """,
                activity.getUserId(),
                activity.getActivityType(),
                activity.getDuration(),
                activity.getCaloriesBurned(), // <- this might be null
                activity.getAdditionalMetrics(),
                activity.getCreatedAt(),
                activity.getUpdatedAt()
        );
    }
}
