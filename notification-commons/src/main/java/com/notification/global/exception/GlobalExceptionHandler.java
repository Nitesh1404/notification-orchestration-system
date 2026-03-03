package com.notification.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.notification.global.exception.custome.DuplicateRequestException;
import com.notification.global.exception.custome.ValidationRequestException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DuplicateRequestException.class)
	public ResponseEntity<?> handleDuplicate(DuplicateRequestException ex){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
	
	@ExceptionHandler(ValidationRequestException.class)
	public ResponseEntity<?> handleValidationException(ValidationRequestException ex){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
}
