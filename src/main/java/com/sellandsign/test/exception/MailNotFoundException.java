package com.sellandsign.test.exception;

public class MailNotFoundException extends RuntimeException {
	public MailNotFoundException(String email) {
		super("Mail not found " + email);
	}
}
