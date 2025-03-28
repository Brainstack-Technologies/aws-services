package com.example.aws.sqs.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class SQSConfig {
	
	 @Value("${aws.accessKeyId}")
	    private String accessKey;

	    @Value("${aws.secretAccessKey}")
	    private String secretKey;

	    @Value("${aws.region}")
	    private String region;

	 @Bean
	 public SqsClient sqsClient() {
	        return SqsClient.builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(accessKey, secretKey)))
            .build();
	    }
}
