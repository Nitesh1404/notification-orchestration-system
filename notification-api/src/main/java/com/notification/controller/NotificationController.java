package com.notification.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notification.dto.ReqApi;
import com.notification.dto.RespApi;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class NotificationController {

	@PostMapping("notification")
	public ResponseEntity<RespApi> notification(@PathVariable ReqApi requestMsg){
		
		log.info("Request recieved for notification : " + requestMsg);
		
		return ResponseEntity.status(HttpStatus.OK).body(new RespApi());
	}
	
}
