package com.sellandsign.test.config;

import com.sellandsign.test.security.AuthSuccessHandler;
import com.sellandsign.test.security.JWTAuthorizationFilter;
import com.sellandsign.test.security.apikey.APIKeyAuthFilter;
import com.sellandsign.test.security.apikey.APIKeyAuthenticationProvider;
import com.sellandsign.test.security.user.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class MultipleHttpSecurity {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final UserDetailsServiceImpl userDetailsService;

	public MultipleHttpSecurity(BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailsServiceImpl userDetailsService) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userDetailsService = userDetailsService;
	}

	@Configuration
	@Order(1)
	public class ApiSecurityAdapter extends WebSecurityConfigurerAdapter {

		private final APIKeyAuthenticationProvider apiKeyAuthenticationProvider;

		public ApiSecurityAdapter(APIKeyAuthenticationProvider apiKeyAuthenticationProvider) {
			this.apiKeyAuthenticationProvider = apiKeyAuthenticationProvider;
		}

		@Override
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
			auth.authenticationProvider(apiKeyAuthenticationProvider);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/v1/**") //<= Security only available for /api/**
					.authorizeRequests()
					.anyRequest().authenticated()
					.and()
					.addFilterBefore(new APIKeyAuthFilter(authenticationManager()), JWTAuthorizationFilter.class)
					.addFilter(new JWTAuthorizationFilter(authenticationManager()))
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		}
	}

	@Configuration
	public class WebSecurityAdapter extends WebSecurityConfigurerAdapter {

		@Bean
		public AuthenticationSuccessHandler authSuccessHandler(){
			return new AuthSuccessHandler();
		}

		@Bean
		public DaoAuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
			auth.setUserDetailsService(userDetailsService);
			auth.setPasswordEncoder(bCryptPasswordEncoder);
			return auth;
		}

		private  final String[] AUTH_WHITELIST = {
				"/swagger-resources",
				"/swagger-resources/**",
				"/configuration/ui",
				"/configuration/security",
				"/swagger-ui.html",
				"/webjars/**",
				"/v3/api-docs/**",
				"/swagger-ui/**"
		};


		@Override
		public void configure(AuthenticationManagerBuilder auth) {
			auth.authenticationProvider(authenticationProvider());
		}
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
					.antMatchers("/signup").permitAll()
					.antMatchers(AUTH_WHITELIST).permitAll()
					.antMatchers("/user").hasAuthority("ADMIN")
					.antMatchers("/recipients/add").hasAnyAuthority("ADMIN","WRITE")
					.anyRequest().authenticated()
					.and()
					.formLogin()
					.loginPage("/login")
					.successHandler(authSuccessHandler())
					.permitAll()
					.and()
					.logout()
					.invalidateHttpSession(true)
					.clearAuthentication(true)
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.logoutSuccessUrl("/login")
					.and()
					.csrf();
		}
	}
}
