package com.sakda.chineselearning.exception;

import java.util.HashMap;
import java.util.Map;

import com.sakda.chineselearning.ChineseLearningApiApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ChineseLearningApiApplication chineseLearningApiApplication;

    GlobalExceptionHandler(ChineseLearningApiApplication chineseLearningApiApplication) {
        this.chineseLearningApiApplication = chineseLearningApiApplication;
    }
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, String> handleResourceNotFoundException (
			ResourceNotFoundException ex) {
		
		Map<String, String> errorReponse = Map.of("message", ex.getMessage());
		
		return errorReponse;
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handleValidationException (MethodArgumentNotValidException ex){
		
		Map<String, String> errors = new HashMap<>();
		
		ex.getBindingResult().getFieldErrors()
        .forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
		
		return errors;
	}

}
