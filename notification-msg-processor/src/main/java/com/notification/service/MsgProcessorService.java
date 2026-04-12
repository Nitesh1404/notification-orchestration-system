package com.notification.service;

import java.net.http.HttpClient;
import java.security.Provider;
import java.time.LocalDateTime;
import java.util.List;

import com.google.gson.Gson;
import com.notification.constants.NotificationStatus;
import com.notification.domain.ProviderRequest;
import com.notification.domain.ProviderResponse;
import com.notification.entity.NotificationAttempt;
import com.notification.entity.TemplateData;
import com.notification.repository.*;
import jakarta.transaction.Transactional;
import jdk.jfr.StackTrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notification.entity.NotificationChannelReq;
import com.notification.entity.ProviderConfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

import javax.print.DocFlavor;

@Slf4j
@Service
public class MsgProcessorService {
    @Autowired
    private ProviderConfigRepository providerConfigRepository;

    @Autowired
    private NotificationChannelRepository notificationChannelRepository;

    @Autowired
    private AttemptRepository attemptRepository;

    @Autowired
    private NotificationEventRepository notificationEventRepository;

    @Autowired
    private TemplateDataRepository templateDataRepository;

    @Autowired
    private WebClient webClient;

    private final Gson gson = new Gson();

    public void processPendingNotification() {
        log.info("Processing for pending notification begin ");

        List<NotificationChannelReq> notificationChannelReqs = notificationChannelRepository.fetchPendingChannels();

        for (NotificationChannelReq channel : notificationChannelReqs) {
            log.info("sending channel request  to provider......");
            sendRequest(channel);
        }
    }

    @Transactional
    private void sendRequest(NotificationChannelReq notificationChannelReq) {

        ProviderResponse providerResponse = new ProviderResponse();

        try {

            /*Fetch the provider*/
            List<ProviderConfig> providers = providerConfigRepository.fetchProviders(notificationChannelReq.getChannelType());

            /*Fetch Template Data*/
            TemplateData templateData = templateDataRepository.findByTemplateCode(notificationChannelReq.getTemplateCode())
                    .orElseThrow(() -> new IllegalStateException("Template not found for code : " + notificationChannelReq.getTemplateCode()));

            /*Fetch the message body from Template Data*/
            String templateBody = templateData.getTemplateBody();

            /*Build the provider Request*/
            ProviderRequest providerRequest = new ProviderRequest();
            providerRequest.setTo(notificationChannelReq.getRecipient());
            providerRequest.setBody(templateBody);

            //Get the provider Url
            String providerUrl = providers.get(0).getBaseUrl();

            /*Call the provider*/

            providerResponse = webClient.post().uri(providerUrl)
                    .bodyValue(providerRequest)
                    .retrieve()
                    .bodyToMono(ProviderResponse.class)
                    .block();

            log.info("Response Receive from Provider : {}" , gson.toJson(providerResponse));

            /*Insert into Notification Attempt*/
            NotificationAttempt lastAttempt = attemptRepository.findTopByChannelIdOrderByAttemptNumberDesc(notificationChannelReq.getId());

            int nextAttempt = (lastAttempt == null) ? 1 : lastAttempt.getAttemptNumber() + 1;

            NotificationAttempt attempt = new NotificationAttempt();
            attempt.setAttemptNumber(nextAttempt);
            attempt.setRequestPayload(gson.toJson(providerRequest));
            attempt.setResponsePayload(gson.toJson(providerResponse));
            attempt.setProvider(providers.get(0).getProviderName());
            attempt.setStatus(providerResponse.getStatus());
            attempt.setChannel(notificationChannelReq);
            attempt.setCreatedAt(LocalDateTime.now());

            // save attempt
            attemptRepository.save(attempt);

            //update channel
            if (providerResponse.getStatus().equals(NotificationStatus.SUCCESS.toString())) {
                notificationChannelReq.setStatus(NotificationStatus.SUCCESS.toString());
            } else {
                notificationChannelReq.setStatus(NotificationStatus.FAILED.toString());
                notificationChannelReq.setNextRetryAt(LocalDateTime.now().plusMinutes(5));
            }

            notificationChannelReq.setRetryCount(nextAttempt);

            notificationChannelRepository.save(notificationChannelReq);

            List<String> statuses = notificationChannelRepository
                    .findStatusesByEventId(notificationChannelReq.getEvent().getId());

            boolean anyFailed = statuses.stream()
                    .anyMatch(s -> s.equals(NotificationStatus.FAILED.toString()));

            String eventStatus = anyFailed
                    ? NotificationStatus.FAILED.toString()
                    : NotificationStatus.SUCCESS.toString();

            notificationEventRepository.updateEventStatus(notificationChannelReq.getEvent().getId(), notificationChannelReq.getStatus());

        } catch (Exception e) {
            log.info("Exception occurred while sending request to provider : {}", String.valueOf(e));
        }

    }

}
