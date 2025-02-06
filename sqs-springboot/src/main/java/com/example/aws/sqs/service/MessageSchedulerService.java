package com.example.aws.sqs.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
public class MessageSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(MessageSchedulerService.class);
    private final SqsClient sqsClient;
    @Value("${aws.sqs.queueUrl}") 
    private String queueUrl;

    public MessageSchedulerService(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    @Scheduled(fixedRate = 60000) // Every 60 seconds
    public void sendScheduledMessage() {
        String messageBody = "Scheduled message at " + System.currentTimeMillis();

        SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(messageBody)
                .delaySeconds(0)
                .build();

        sqsClient.sendMessage(sendMessageRequest);
        System.out.println("Sent message to SQS: " + messageBody);
    }
}

