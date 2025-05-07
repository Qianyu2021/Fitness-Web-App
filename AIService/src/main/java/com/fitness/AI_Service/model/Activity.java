package com.fitness.AI_Service.model;


import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class Activity {

    private String id;
    private String userId;
    private String activityType;
    private Integer caloriesBurned;
    private Integer duration;
    private LocalDateTime startTime;
    private Map<String, String> additionalMetrics;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
