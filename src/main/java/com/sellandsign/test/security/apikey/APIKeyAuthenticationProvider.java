package com.sellandsign.test.security.apikey;

import com.sellandsign.test.repository.h2.apikey.ApiKeyRepository;
import com.sellandsign.test.model.apikey.ApiKey;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Test the Authentication by calling apiKeyRepository.
 */
@Component
public class APIKeyAuthenticationProvider implements AuthenticationProvider {

	private  final ApiKeyRepository apiKeyRepository;

	public APIKeyAuthenticationProvider(ApiKeyRepository apiKeyRepository) {
		this.apiKeyRepository = apiKeyRepository;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Optional<ApiKey> apikey = apiKeyRepository.findByKey((UUID) authentication.getPrincipal());
		if(apikey.isPresent()) {
			authentication.setAuthenticated(true);
			return authentication;
		} else {
			throw new InternalAuthenticationServiceException("token not valid");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (APIKeyAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
