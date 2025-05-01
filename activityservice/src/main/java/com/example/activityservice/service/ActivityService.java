package com.example.activityservice.service;


import com.example.activityservice.dto.ActivityRequest;
import com.example.activityservice.dto.ActivityResponse;
import com.example.activityservice.model.Activity;
import com.example.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //only if you are generating final constructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    public ActivityResponse trackActivity(ActivityRequest activityRequest) {
        // Logic to track activity
        // For now, just return the request as a response
        Activity activity = Activity.builder()
                .userId(activityRequest.getUserId())
                .activityType(activityRequest.getActivityType())
                .duration(activityRequest.getDuration())
                .caloriesBurned(activityRequest.getCaloriesBurned())
                .additionalMetrics(activityRequest.getAdditionalMetrics())
                .createdAt(activityRequest.getCreatedAt())
                .updatedAt(activityRequest.getUpdatedAt())
                .build();
        //save the activity to the database
        Activity savedActivity = activityRepository.save(activity);

        return savedActivityToResponse(savedActivity);
    }

    private ActivityResponse savedActivityToResponse(Activity savedActivity) {

        // Convert the saved activity to a response object
        ActivityResponse activityResponse = new ActivityResponse();
        activityResponse.setId(savedActivity.getId());
        activityResponse.setUserId(savedActivity.getUserId());
        activityResponse.setActivityType(savedActivity.getActivityType());
        activityResponse.setDuration(savedActivity.getDuration());
        activityResponse.setCaloriesBurned(savedActivity.getCaloriesBurned());
        activityResponse.setCreatedAt(savedActivity.getCreatedAt());
        activityResponse.setAdditionalMetrics(savedActivity.getAdditionalMetrics());
        activityResponse.setUpdatedAt(savedActivity.getUpdatedAt());
        return activityResponse;
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        // Logic to get user activities
        List<Activity> activities = activityRepository.findByUserId(userId);
        return activities.stream()
                .map(this::savedActivityToResponse)
                .collect(Collectors.toList());
    }

    public ActivityResponse getActivityById(String activityId) {
        // Logic to get activity by ID
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found"));
        return savedActivityToResponse(activity);
    }

    public ActivityResponse getActivityByActivityId(String activityId) {
        // Logic to get activity by activity type
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found"));
        return savedActivityToResponse(activity);
    }

}
