package com.notification.domain;

import com.notification.constants.NotificationStatus;
import com.notification.constants.ProviderType;

import lombok.Data;

@Data
public class NotificationRequest {
	private String notificationId;
	private String eventId;
	private String reciepent;
	private Enum<ProviderType> provider;
	private Enum<NotificationStatus> status;
}
