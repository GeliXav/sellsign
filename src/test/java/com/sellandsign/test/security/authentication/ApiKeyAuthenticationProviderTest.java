package com.sellandsign.test.security.authentication;

import com.sellandsign.test.repository.h2.apikey.ApiKeyRepository;
import com.sellandsign.test.model.apikey.ApiKey;
import com.sellandsign.test.security.apikey.APIKeyAuthenticationProvider;
import com.sellandsign.test.security.apikey.APIKeyAuthenticationToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApiKeyAuthenticationProviderTest {
	private APIKeyAuthenticationProvider apiKeyAuthenticationProvider;
	private ApiKeyRepository apiKeyRepository;

	@BeforeEach
	public void init() {
		apiKeyRepository = mock(ApiKeyRepository.class);
		apiKeyAuthenticationProvider = new APIKeyAuthenticationProvider(apiKeyRepository);
	}

	@Test
	public void testAuthenticationSuccess() {
		APIKeyAuthenticationToken authenticationToken = new APIKeyAuthenticationToken(UUID.randomUUID());
		when(apiKeyRepository.findByKey(any())).thenReturn(Optional.of(new ApiKey()));
		Authentication authentication = apiKeyAuthenticationProvider.authenticate(authenticationToken);
		assertTrue(authentication.isAuthenticated());
	}

	@Test
	public void testAuthenticationFailure() {
		APIKeyAuthenticationToken authenticationToken = new APIKeyAuthenticationToken(UUID.randomUUID());
		when(apiKeyRepository.findByKey(any())).thenReturn(Optional.empty());
		apiKeyAuthenticationProvider.authenticate(authenticationToken);
		Assertions.assertThrows(InternalAuthenticationServiceException.class, () -> {
			apiKeyAuthenticationProvider.authenticate(authenticationToken);
		});
	}
}
