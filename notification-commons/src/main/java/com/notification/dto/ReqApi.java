package com.notification.dto;


import java.util.List;
import java.util.Map;

import com.notification.constants.NotificationChannel;
import com.notification.domain.Recipient;

import lombok.Data;

@Data
public class ReqApi {
		private String clientId;
		private String eventType;
		private Enum<NotificationChannel> channelType;
		private String templateCode;
		private List<Recipient> recipient;
		private Map<String, Object> templateData;
		private String idempotencyKey;
		private String referenceId;
	
}
