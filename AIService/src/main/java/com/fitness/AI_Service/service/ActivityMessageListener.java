package com.fitness.AI_Service.service;

import com.fitness.AI_Service.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAIService activityAIService;
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void processActivity(Activity activity) {
        log.info("Received activity message for processing: {}", activity.getId());
        try {
            log.info("Received activity message for processing: {}", activity.getId());
            activityAIService.generateRecommendation(activity);
        } catch (Exception e) {
            log.error("Failed to process activity: {}", activity.getId(), e);
            throw new AmqpRejectAndDontRequeueException("Processing failed", e);
        }
    }
}
