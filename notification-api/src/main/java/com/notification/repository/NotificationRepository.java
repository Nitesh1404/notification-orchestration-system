package com.notification.repository;

import org.springframework.stereotype.Repository;

import com.notification.domain.NotificationRequest;
import com.notification.dto.ReqApi;

@Repository
public class NotificationRepository {

	String sql_save_event = "";

	public void saveNotificationEvent(ReqApi reqApi, NotificationRequest notificationRequest) {
		Object[] params = { notificationRequest.getNotificationId(), notificationRequest.getEventId(),
				reqApi.getChannelType(), notificationRequest.getReciepent(), reqApi.getTemplateCode(),
				notificationRequest.getProvider(), notificationRequest.getStatus() };
		
	}
}
