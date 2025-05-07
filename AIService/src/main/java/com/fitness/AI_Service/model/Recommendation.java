package com.fitness.AI_Service.model;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;import jdk.jfr.DataAmount;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recommendation {

    @Id
    private String id;

    private String userId;

    private String activityId;
    private Integer duration;
    private String recommendation;
    private String activityType;
    private String activityName;
    private String reason;
    @CreatedDate
    private LocalDateTime createdAt;

    private List<Recommendation> suggestions;
}
