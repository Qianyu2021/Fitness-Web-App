package com.example.activityservice.controler;


import com.example.activityservice.dto.ActivityRequest;
import com.example.activityservice.dto.ActivityResponse;
import com.example.activityservice.repository.ActivityRepository;
import com.example.activityservice.service.ActivityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@AllArgsConstructor
public class ActivityController {

    private final ActivityRepository activityRepository;
    private ActivityService activityService;
    @PostMapping
    //this is to track the activity of the user
    //this is to handle HTTP response
    public ResponseEntity<ActivityResponse> trackActivity(@Valid @RequestBody ActivityRequest activityRequest) {

        //RequestBody deserialize the JSON into the java object
        //ResponseEntity returns the response to the HTTP request
        return ResponseEntity.ok(activityService.trackActivity(activityRequest));
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getUserActivities(@RequestHeader("X-User-ID") String userId) {
        //this is to get the user activities
        return ResponseEntity.ok(activityService.getUserActivities(userId));
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> getActivityById(@PathVariable String activityId) {
        //this is to get the activity by ID
        return ResponseEntity.ok(activityService.getActivityById(activityId));
    }

    @GetMapping("/activityType/{activityId}")
    public ResponseEntity<ActivityResponse> getActivityByActivityId(@PathVariable String activityId) {
        //this is to get the activity by activity type
        return ResponseEntity.ok(activityService.getActivityByActivityId(activityId));
    }


}
