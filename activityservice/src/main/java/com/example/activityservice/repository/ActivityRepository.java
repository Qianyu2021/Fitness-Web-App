package com.example.activityservice.repository;


import com.example.activityservice.model.Activity;
import com.example.activityservice.model.ActivityType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {
    // Custom query methods can be defined here if needed
    // For example, find activities by userId or activityType
    List<Activity> findByUserId(String userId);
    List<Activity> findByActivityType(ActivityType activityType);
}
