package com.sellandsign.test.security.apikey;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * Filter for filtering apikey with header x-apikey.
 * Returns a JWT on success, 403 on failure.
 */
public class APIKeyAuthFilter extends AbstractAuthenticationProcessingFilter {
	private final String authHeaderName = "X-API-KEY";
	public final static String LOGIN_URL = "/v1/authToken";

	private final AuthenticationManager authenticationManager;

	public APIKeyAuthFilter(AuthenticationManager authenticationManager) {
		super(new AntPathRequestMatcher(LOGIN_URL));
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		if (request.getHeader(authHeaderName) == null) {
			throw new InternalAuthenticationServiceException("The header X-API-KEY must be set to authenticate");
		}
		UUID token = UUID.fromString(request.getHeader(authHeaderName));
		APIKeyAuthenticationToken authRequest = new APIKeyAuthenticationToken(token);
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
		return authenticationManager.authenticate(authRequest);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req,
	                                        HttpServletResponse res,
	                                        FilterChain chain,
	                                        Authentication auth) throws IOException {
		String token = JWT.create()
				               .withExpiresAt(new Date(System.currentTimeMillis() + 900000))
				               .withSubject(auth.getPrincipal().toString())
				               .sign(Algorithm.HMAC512("secret".getBytes()));


		res.getWriter().write(token);
		res.getWriter().flush();
	}
}
