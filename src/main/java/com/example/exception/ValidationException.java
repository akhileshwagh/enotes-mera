
package com.example.exception;

import java.util.Map;

import org.springframework.stereotype.Component;

//@Component
public class ValidationException extends RuntimeException{
	
	private Map<String, Object> error;

	public ValidationException(Map<String, Object> error) {
		super("Validation failed");
		this.error = error;
	}
	
	public Map<String, Object> getErrors(){
		return error;
	}
}