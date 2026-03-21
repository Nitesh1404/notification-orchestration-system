package com.notification.service;

import java.security.Provider;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notification.entity.NotificationChannelReq;
import com.notification.entity.ProviderConfig;
import com.notification.repository.NotificationChannelRepository;
import com.notification.repository.ProviderConfigRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MsgProcessorService {

	@Autowired
	private NotificationChannelRepository channelRepository;
	
	@Autowired ProviderConfigRepository providerConfigRepository;
	
	public void processPendingNotification() {
		log.info("Processing for pending notification begin ");
		
		List<NotificationChannelReq> notificationChannelReqs = channelRepository.fetchPendingChannels();
		
		for(NotificationChannelReq channel : notificationChannelReqs) {
			log.info("sending channel request  to provider......");
			sendRequest(channel);
		}
	}
	
	private void sendRequest(NotificationChannelReq notificationChannelReq) {
		
		/*Fetch the provider*/
		List<ProviderConfig> providers = providerConfigRepository.fetchProviders(notificationChannelReq.getChannelType());
		
		
	}
	
}
