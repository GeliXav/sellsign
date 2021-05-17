package com.sellandsign.test.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
		// Get the role of logged in user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String role = auth.getAuthorities().toString();
		String targetUrl = "";

		switch (role) {
			case "[ADMIN]":
				targetUrl="/user";
				break;
			case "[READ_ONLY]":
				targetUrl="/recipients";
				break;
			case "[WRITE]":
				targetUrl="/recipients/add";
				break;
		}
		return targetUrl;
	}
}
