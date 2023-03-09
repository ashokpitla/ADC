package com.app.Springsecurityapp.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.Springsecurityapp.entity.User;
import com.app.Springsecurityapp.entity.VerificationToken;
import com.app.Springsecurityapp.model.UserModel;
import com.app.Springsecurityapp.repositroy.UserRepository;
import com.app.Springsecurityapp.repositroy.VerificationTokenRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository repository;
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private VerificationTokenRepository tokenRepository;
	
	@Override
	public User registerUser(UserModel model) {
		
		User user=new User();
		user.setEmail(model.getEmail());
		user.setFirstName(model.getFirstName());
		user.setLastName(model.getLastName());
		user.setRole("USER");
		user.setPassword(encoder.encode(model.getPassword()));
		repository.save(user);
		return user;
	}

	@Override
	public void saveVerificationTokenForUser(String token, User user) {

		VerificationToken tokenVerificationToken=new VerificationToken(user,token);
		tokenRepository.save(tokenVerificationToken);
		
	}

	@Override
	public String validateVerifycationToken(String token) {
		VerificationToken verificationToken=tokenRepository.findByToken(token);
		if(verificationToken==null) {
			return "invalid";
		}
		User user=verificationToken.getUser();
		Calendar cal=Calendar.getInstance();
		if((verificationToken.getExperationTime().getTime()-cal.getTime().getTime())<=0) {
			tokenRepository.delete(verificationToken);
			return "expired";
		}
		user.setEnabled(true);
		repository.save(user);
		return "valid";
	}

	@Override
	public VerificationToken generateNewVerificationToken(String oldToken) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
