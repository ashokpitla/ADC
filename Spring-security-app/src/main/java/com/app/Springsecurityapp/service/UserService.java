package com.app.Springsecurityapp.service;

import com.app.Springsecurityapp.entity.User;
import com.app.Springsecurityapp.entity.VerificationToken;
import com.app.Springsecurityapp.model.UserModel;

public interface UserService {

	public User registerUser(UserModel model);

	public void saveVerificationTokenForUser(String token, User user);

	public  String validateVerifycationToken(String token);

	public VerificationToken generateNewVerificationToken(String oldToken);

}
