package com.sellandsign.test.error;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

/**
 * CustomErrorResponse
 */
@Getter
@Setter
public class CustomErrorResponse {
	private HttpStatus status;
	private String message;
}
