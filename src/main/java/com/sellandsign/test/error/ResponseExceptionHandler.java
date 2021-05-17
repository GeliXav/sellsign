package com.sellandsign.test.error;

import com.sellandsign.test.exception.DuplicateUserException;
import com.sellandsign.test.exception.MailNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(MailNotFoundException.class)
	protected ResponseEntity<CustomErrorResponse> notFoundError(MailNotFoundException exception) {
		CustomErrorResponse apiError = new CustomErrorResponse();
		apiError.setMessage(exception.getMessage());
		apiError.setStatus(HttpStatus.NOT_FOUND);
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(DuplicateUserException.class)
	protected ResponseEntity<CustomErrorResponse> notFoundError(DuplicateUserException exception) {
		CustomErrorResponse apiError = new CustomErrorResponse();
		apiError.setMessage(exception.getMessage());
		apiError.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
		return buildResponseEntity(apiError);
	}


	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	                                                              HttpHeaders headers, HttpStatus status, WebRequest request) {
		String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getField();
		CustomErrorResponse apiError = new CustomErrorResponse();
		apiError.setMessage(errorMessage + "is missing in the payload !");
		apiError.setStatus(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(apiError, status);
	}
	private ResponseEntity<CustomErrorResponse> buildResponseEntity(CustomErrorResponse apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}
