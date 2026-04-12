package com.notification.scheduler;

import com.notification.service.MsgProcessorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationScheduler {

	@Autowired
	private MsgProcessorService msgProcessorService;

	@Value("${schedular.fixed.delay:2000}")
	String fixedDelay;

	@Scheduled(fixedDelayString = "${schedular.fixed.delay:2000}")
	public void processNotification() {
		log.info("Processing message with some fixed delay : {}" , fixedDelay);
		msgProcessorService.processPendingNotification();
	}
}
