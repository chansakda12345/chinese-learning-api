package com.sakda.chineselearning.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String> handleResourceNotFoundException (
			ResourceNotFoundException ex) {
		
		Map<String, String> errorReponse = Map.of("message", ex.getMessage());
		
		return errorReponse;
	}

}
