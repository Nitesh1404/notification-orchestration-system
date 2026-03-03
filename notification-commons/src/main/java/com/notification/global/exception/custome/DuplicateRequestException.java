package com.notification.global.exception.custome;


public class DuplicateRequestException extends RuntimeException{
	
	public DuplicateRequestException(String message) {
		super(message);
	}

}
