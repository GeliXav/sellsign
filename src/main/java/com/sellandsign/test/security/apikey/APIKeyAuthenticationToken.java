package com.sellandsign.test.security.apikey;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.UUID;

/**
 * Apikey Token class.
 */
public class APIKeyAuthenticationToken extends AbstractAuthenticationToken {

	private UUID token;

	public APIKeyAuthenticationToken(UUID token) {
		super(null);
		this.token = token;
	}

	public Object getCredentials() {
		return null;
	}

	public Object getPrincipal() {
		return token;
	}
}
