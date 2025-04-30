package com.example.activityservice.dto;


import com.example.activityservice.model.ActivityType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;


@Data
public class ActivityRequest {
    @NotBlank(message = "User ID cannot be blank")
    private String userId;

    @NotNull(message = "Activity Type cannot be null")
    private ActivityType activityType;

    @NotNull(message = "Start Time cannot be null")
    @Positive(message = "Start Time must be a positive value")
    private Integer duration;

    @NotNull(message = "Calories Burned cannot be null")
    @Min(value = 0, message = "Calories Burned must be a positive value")
    private Integer caloriesBurned;

    private Map<String, Object> additionalMetrics;

    @NotNull(message = "Start Time cannot be null")
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
