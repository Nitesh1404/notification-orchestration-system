package com.notification.webhook.service;

import com.google.gson.Gson;
import com.notification.constants.NotificationStatus;
import com.notification.domain.ProviderResponse;
import com.notification.entity.NotificationChannelReq;
import com.notification.repository.NotificationChannelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WebhookService {

    private final Gson gson = new Gson();

    private NotificationChannelRepository notificationChannelRepository;

    public void process(ProviderResponse providerResponse){
        log.info("Webhook service called for response: {} " , gson.toJson(providerResponse));

        /*get the channel for the message id */
        NotificationChannelReq channelReq = notificationChannelRepository.findByProviderRefId(providerResponse.getMsgId())
                .orElseThrow();

        if(providerResponse.getStatus().equals(NotificationStatus.SUCCESS.getStatus())){
            channelReq.setStatus(NotificationStatus.DELIVERED.getStatus());
        }

        notificationChannelRepository.save(channelReq);
    }

}
