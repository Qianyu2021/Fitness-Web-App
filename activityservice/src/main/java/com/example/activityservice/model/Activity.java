package com.example.activityservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "activities") // Marks this class as a MongoDB document, stored in "activities" collection
@Data
@Builder  // Lombok: generates a builder pattern implementation
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    @Id  // Marks this field as the document's primary key in MongoDB@Id  // Marks this field as the document's primary key in MongoDB@Id  // Marks this field as the document's primary key in MongoDB
    private String id;

    private String userId;
    private ActivityType activityType;
    private Integer caloriesBurned;
    private Integer duration;

    @Field("metrics")  // Specifies the field name in MongoDB document
    private Map<String, Object> additionalMetrics;

    @CreatedDate //hibernate, this is for non relational database
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
