package com.notification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.notification.dto.ReqApi;
import com.notification.dto.RespApi;
import com.notification.service.NotificationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;

	@PostMapping("${api.notification.endpoint}")
	public ResponseEntity<RespApi> notification(@PathVariable ReqApi requestMsg){
		
		log.info("Request recieved for notification : " + requestMsg);
		
		RespApi respApi = notificationService.process(requestMsg);
		
		log.info("Request processed successfully : " + new Gson().toJson(respApi));
		
		return ResponseEntity.status(HttpStatus.OK).body(respApi);
	}
	
}
