package com.notification.webhook.controller;

import com.google.gson.Gson;
import com.notification.domain.ProviderResponse;
import com.notification.webhook.service.WebhookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class WebhookController {
    private final Gson gson = new Gson();

    private WebhookService webhookService;

    @PostMapping(value = "/notificationWebhook")
    public ResponseEntity<Void> acceptProviderResponse(@PathVariable ProviderResponse providerResponse){

        log.info("Response received from provider : {}" , gson.toJson(providerResponse));

        webhookService.process(providerResponse);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

