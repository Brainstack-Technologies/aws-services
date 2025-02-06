package com.example.aws.sqs.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
@Service
public class MessageConsumer {

private final SqsClient sqsClient;
    
    @Value("${aws.sqs.queueUrl}")
    private String queueUrl; 

    public MessageConsumer(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    @Scheduled(cron = "0 0/2 * * * *")  
    public void pollQueueForMessages() {
    	ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(3)  
                .waitTimeSeconds(10)    
                .build();

    	 ReceiveMessageResponse response = sqsClient.receiveMessage(receiveMessageRequest);
    	 
        // Process received messages
        if (response.messages() != null && !response.messages().isEmpty()) {
            for (Message message : response.messages()) {
                System.out.println("Received message: " + message.body());
                deleteMessageFromQueue(message);  
            }
        } else {
            System.out.println("No messages received");
        }
    }

    private void deleteMessageFromQueue(Message message) {
        DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(message.receiptHandle())
                .build();

        sqsClient.deleteMessage(deleteMessageRequest);
        System.out.println("Message deleted from queue");
    }
}

