package com.notification.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.notification.constants.NotificationStatus;
import com.notification.domain.Recipient;
import com.notification.dto.ReqApi;
import com.notification.dto.RespApi;
import com.notification.entity.NotificationChannelReq;
import com.notification.entity.NotificationEvent;
import com.notification.global.exception.custome.DuplicateRequestException;
import com.notification.global.exception.custome.ValidationRequestException;
import com.notification.repository.NotificationEventRepository;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NotificationService {
	
	@Autowired
	private NotificationEventRepository eventRepository;
	
	private Gson gson = new Gson();

	public RespApi process(ReqApi reqApi) {
		
		RespApi respApi = new RespApi();
		
		/* validate the incoming request*/
		validateRequest(reqApi);
		
		/*check for duplicate request*/
		checkDuplicate(reqApi);
		
		NotificationEvent event = buildNotificationEvent(reqApi);
		
		List<NotificationChannelReq> channles = buildListChannels(reqApi ,event);
		
		event.setChannels(channles);
		
		/*
		 * Save Notification Event
		 * 1. It will save in Notification_event tables and 
		 * 2. Notification_channel Table due to JPA cCascadeType.ALL
		 */
		eventRepository.save(event);
		
		/*Building final response*/
		respApi.setStatus(NotificationStatus.RECIEVED.getStatus());
		respApi.setStatusDesc(NotificationStatus.RECIEVED.getDescription());
		respApi.setEventId(event.getEventId());
		
		return respApi;
	}
	
	private void validateRequest(ReqApi reqApi) {

	    log.info("Validating the request");

	    if (StringUtils.isBlank(reqApi.getClientId())) {
	        throw new ValidationRequestException("Client ID is mandatory");
	    }

	    if (StringUtils.isBlank(reqApi.getReferenceId())) {
	        throw new ValidationRequestException("Reference ID is mandatory");
	    }

	    if (reqApi.getChannelType() == null) {
	        throw new ValidationRequestException("Channel type is mandatory");
	    }

	    if (reqApi.getTemplateData() == null || reqApi.getTemplateData().isEmpty()) {
	        throw new ValidationRequestException("Template data is mandatory");
	    }

	    if (StringUtils.isBlank(reqApi.getEventType())) {
	        throw new ValidationRequestException("Event Type is mandatory");
	    }
	}
	
	private void checkDuplicate(ReqApi reqApi) {
		log.info("Checking duplicate request for key : " + reqApi.getIdempotencyKey());
		
		String idempotencyKey = reqApi.getIdempotencyKey();
		boolean isExist = eventRepository.existsByIdempotencyKey(idempotencyKey);
		if(isExist) {
			throw new DuplicateRequestException("Duplicate idempotency key");
		}
	}
	
	private NotificationEvent buildNotificationEvent(ReqApi reqApi) {
		NotificationEvent notificationEvent = new NotificationEvent();
		
		notificationEvent.setEventId(genEventId());
		notificationEvent.setClientId(reqApi.getClientId());
		notificationEvent.setEventType(reqApi.getEventType());
		notificationEvent.setStatus(NotificationStatus.PENDING.toString());
		notificationEvent.setReferenceId(reqApi.getReferenceId());
		notificationEvent.setIdempotencyKey(reqApi.getIdempotencyKey());
		
		notificationEvent.setCreatedAt(LocalDateTime.now());
		notificationEvent.setUpdatedAt(LocalDateTime.now());
		
		log.info("Generated Notification Event is : " + gson.toJson(notificationEvent));
		
		return notificationEvent;
	}
	
	private List<NotificationChannelReq> buildListChannels(ReqApi  reqApi , NotificationEvent event){
		List<NotificationChannelReq> channelList = new ArrayList<>();
		
		for(Recipient recipient : reqApi.getRecipient()) {
			NotificationChannelReq channel = new NotificationChannelReq();
			
			channel.setChannelType(reqApi.getChannelType().name());
	        channel.setRecipient(recipient.getValue());
	        channel.setTemplateCode(reqApi.getTemplateCode());
	        channel.setStatus(NotificationStatus.PENDING.name());
	        channel.setRetryCount(0);

	        channel.setCreatedAt(LocalDateTime.now());
	        channel.setEvent(event);     // VERY IMPORTANT
	        channel.setUpdatedAt(LocalDateTime.now());

	        channelList.add(channel);
		}
		
		return channelList;
	}
	
	private String genEventId() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
}
