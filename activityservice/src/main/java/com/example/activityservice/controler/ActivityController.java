package com.example.activityservice.controler;


import com.example.activityservice.dto.ActivityRequest;
import com.example.activityservice.dto.ActivityResponse;
import com.example.activityservice.service.ActivityService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activities")
@AllArgsConstructor
public class ActivityController {

    private ActivityService activityService;
    @PostMapping
    //this is to track the activity of the user
    //this is to handle HTTP response
    public ResponseEntity<ActivityResponse> trackActivity(@Valid @RequestBody ActivityRequest activityRequest) {

        //RequestBody deserialize the JSON into the java object
        //ResponseEntity returns the response to the HTTP request
        return ResponseEntity.ok(activityService.trackActivity(activityRequest));
    }
}
