package com.provider.main.service;

import com.google.gson.Gson;
import com.notification.constants.NotificationStatus;
import com.notification.domain.ProviderRequest;
import com.notification.domain.ProviderResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Slf4j
@Data
@Service
public class StubService {

    @Autowired
    private WebClient webClient;

    public void processStub(ProviderRequest providerRequest, String messageId) {
        log.info("Processing service in provider stub : for request message : {} ", new Gson().toJson(providerRequest));

        ProviderResponse providerResponse = new ProviderResponse();

        providerResponse.setMsgId(messageId);
        providerResponse.setStatus(NotificationStatus.SUCCESS.getStatus());

        webClient.post().uri(providerRequest.getWebhookUrl())
                .bodyValue(providerResponse)
                .retrieve();

        log.info("Response sent to webhook layer successfully");

    }

}
