package com.apiplatform.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.apiplatform.dto.ResponseStructure;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ResponseStructure<String>> handleInvalidCredentialsException(InvalidCredentialsException exp){
		
		ResponseStructure<String> response = new ResponseStructure<String>();
		response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
		response.setMessage(exp.getMessage());
		response.setData("FAILURE");
		
		return new ResponseEntity<ResponseStructure<String>>(response,HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ResponseStructure<String>> handleUserAlreadyExistsException(UserAlreadyExistsException exp){
		
		ResponseStructure<String> response = new ResponseStructure<String>();
		response.setStatusCode(HttpStatus.BAD_REQUEST.value());
		response.setMessage(exp.getMessage());
		response.setData("FAILURE");
		
		return new ResponseEntity<ResponseStructure<String>>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(LimitExceededException.class)
	public ResponseEntity<ResponseStructure<String>> handleLimitExceededException(LimitExceededException exp){
		
		ResponseStructure<String> response = new ResponseStructure<String>();
		response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
		response.setMessage(exp.getMessage());
		response.setData("FAILURE");
		
		return new ResponseEntity<ResponseStructure<String>>(response,HttpStatus.TOO_MANY_REQUESTS);
	}
	
}
