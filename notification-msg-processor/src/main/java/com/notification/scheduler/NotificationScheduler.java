package com.notification.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

	@Scheduled(fixedDelay = 2000)
	public void processNotification() {
		
	}
}
