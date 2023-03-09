package com.app.Springsecurityapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.Springsecurityapp.entity.User;
import com.app.Springsecurityapp.entity.VerificationToken;
import com.app.Springsecurityapp.event.RegistrationCompleteEvent;
import com.app.Springsecurityapp.model.UserModel;
import com.app.Springsecurityapp.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RegistrationController {

	@Autowired
	private UserService service;
	@Autowired
	private ApplicationEventPublisher publisher;

	@PostMapping("/registration")
	public String registration(@RequestBody UserModel model, final HttpServletRequest request) {
		User user=service.registerUser(model);
		publisher.publishEvent(new RegistrationCompleteEvent(user,applicationUrl(request)));
		return "User is created";
	}
	
	@GetMapping("/verifyRegistration")
	public String verifyRegistration(@RequestParam("token") String token) throws Exception{
		String result;
		
			result = service.validateVerifycationToken(token);
		
		if(result.equalsIgnoreCase("valid")) {
			return "user verify succesfull";
		}
		else
			return "Bad user";	
	}
	
	@GetMapping("/resendVerifyToken")
	public String resendVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request) {
		VerificationToken verificationToken=service.generateNewVerificationToken(oldToken);
		User user=verificationToken.getUser();
		resendVerificationTokenMail(user,applicationUrl(request),verificationToken);
		return "verification link is sent";
		
	}
	
	private void resendVerificationTokenMail(User user, String applicationUrl,VerificationToken verificationToken) {
		String url=applicationUrl+"/verifyRegistration?token="+verificationToken.getToken();
		log.info("Click the link to verify your account: {}",url);
		
	}

	private String applicationUrl(HttpServletRequest request) {
		return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	}
}
