package com.capstone.catalog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.capstone.catalog.dto.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleRecordNotFound(Exception ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(404, ex.getMessage()));
	}

	@ExceptionHandler({InvalidArgumentException.class,IllegalArgumentException.class})
	public ResponseEntity<ApiResponse<Void>> handleInValidArgument(Exception ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(400, ex.getMessage()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex)
	{

			String message = ex.getBindingResult()
                       .getFieldErrors()
                       .stream()
                       .findFirst()
                       .map(FieldError::getDefaultMessage)
                       .orElse("Validation failed");

		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(400, message));

	}

}
