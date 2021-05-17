package com.sellandsign.test.exception;

public class DuplicateUserException extends RuntimeException {
	public DuplicateUserException(String username) {
		super("Username must be unique" + username);
	}
}
