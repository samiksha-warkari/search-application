package com.api.search.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		logger.info("Inside handleAllExceptions method");
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Server Error", details);
		return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(DataNotFoundException.class)
	public final ResponseEntity<Object> handleDataNotFoundException(DataNotFoundException ex, WebRequest request) {
		logger.info("Inside handleDataNotFoundException method");
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Data Not Found", details);
		return new ResponseEntity<Object>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InValidRequestParameterException.class)
	public final ResponseEntity<Object> handleInValidRequestParameterException(InValidRequestParameterException ex,
			WebRequest request) {
		logger.info("Inside handleInValidRequestParameterException method");
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Invalid input", details);
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}

}
