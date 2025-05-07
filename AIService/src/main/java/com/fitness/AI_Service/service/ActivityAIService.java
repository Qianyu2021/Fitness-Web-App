package com.fitness.AI_Service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitness.AI_Service.model.Activity;
import com.fitness.AI_Service.model.Recommendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAIService {
    private final GeminiService geminiService;

    public Recommendation generateRecommendation(Activity activity) {
        String prompt = generatePrompt(activity);
        String aiResponse = geminiService.getAnswer(prompt);
        log.info("Generated prompt for activity recommendation: {}", prompt);
        log.info("Raw Gemini AI response: {}", aiResponse);
        return processAiResponse(activity, aiResponse);
    }

    private Recommendation processAiResponse(Activity activity, String aiResponse) {
        // Parse the response from Gemini and convert it to a Recommendation object
        // This is a placeholder implementation, you need to implement the actual parsing logic
       try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(aiResponse);
            JsonNode textNode = rootNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text");

            if(textNode == null || textNode.isMissingNode()){
                return createDefaultRecommendation(activity);
            }
            String jsonConent = textNode.asText()
                    .replaceAll("```json", "")
                    .replaceAll("\\n", "")
                    .trim();
         //   log.info("Parsed JSON content from AI response: {}", jsonConent);
            JsonNode jsonNode = objectMapper.readTree(jsonConent);
            JsonNode recommendationNode = jsonNode.path("recommendations").get(0);
            if(recommendationNode == null || recommendationNode.isMissingNode()){
                return createDefaultRecommendation(activity);
            }
            return Recommendation.builder()
                    .userId(activity.getUserId())
                    .activityType(activity.getActivityType())
                    .duration(activity.getDuration())
                    .recommendation(recommendationNode.path("suggestion").asText())
                    .reason(recommendationNode.path("reason").asText())
                    .build();
        } catch (Exception e) {
            // Handle the error accordingly
            e.printStackTrace();
            return createDefaultRecommendation(activity);
        }
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
          Provide fitness recommendations in JSON format:
              {
                "recommendations": [
                  {
                    "suggestion": "string",
                    "reason": "string"
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

    private Recommendation createDefaultRecommendation(Activity activity) {

        return Recommendation.builder()
                .userId(activity.getUserId())
                .activityType(activity.getActivityType())
                .recommendation("No recommendation available")
                .duration(activity.getDuration())
                .reason("No reason available")
                .build();
    }
}





