package com.app.Springsecurityapp.config;

import java.net.http.HttpRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
public class SecurityConfig {
	
	private static final String[] WHITE_LIST_URLS={"/hello","/register","verifyRegistration","/resendVerityToken*"};

	@Bean
	public PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder(11);
	}
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.cors()
		.and()
		.csrf()
		.disable()
		.authorizeHttpRequests()
		.anyRequest()
		.permitAll();
		return http.build();
	}

}
