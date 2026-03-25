package com.notification.webhook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class WebhookApplication {
    public static void main(String[] args) {
        log.info("Webhook Application is starting..");
        SpringApplication.run(WebhookApplication.class, args);
        log.info("Webhook Application started...");
    }
}
