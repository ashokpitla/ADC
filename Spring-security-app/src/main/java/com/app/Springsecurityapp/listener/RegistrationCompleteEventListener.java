package com.app.Springsecurityapp.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.app.Springsecurityapp.entity.User;
import com.app.Springsecurityapp.event.RegistrationCompleteEvent;
import com.app.Springsecurityapp.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent>{

	@Autowired
	private UserService service;
	
	@Override
	public void onApplicationEvent(RegistrationCompleteEvent event) {
		User user=event.getUser();
		String token=UUID.randomUUID().toString();
		service.saveVerificationTokenForUser(token,user);
		String url=event.getApplicationUrl()+"/verifyRegistration?token="+token;
		log.info("Click the link to verify your account: {}",url);
	}
}
