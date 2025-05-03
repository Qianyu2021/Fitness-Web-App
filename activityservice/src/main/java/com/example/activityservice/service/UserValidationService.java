package com.example.activityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserValidationService {
    @Autowired
    private final WebClient userServiceWebClient;

    public boolean validateUser(String userId) {
        log.info("Validating user with ID: {} by calling API", userId);
        // Call the user service to validate the user
        try {
            return userServiceWebClient.get()
                    .uri("/api/users/" + userId + "/validate")
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (WebClientResponseException e) {
            // Handle the case where the user is not found or any other error
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
                // User not found or invalid request
                throw new RuntimeException("User not found" + userId);
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                // Bad request
                throw new RuntimeException("Bad request" + e.getMessage());
            }
            return false;
        } catch (Exception e) {
            // Handle any other exceptions
            return false;
        }
    }
}
