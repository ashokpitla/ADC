package com.app.Springsecurityapp.event;

import org.springframework.context.ApplicationEvent;

import com.app.Springsecurityapp.entity.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationCompleteEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	private User user;
	private String url;

	public RegistrationCompleteEvent(User user,String url) {
		super(user);
		this.user=user;
		this.url=url;
		
	}

	public String getApplicationUrl() {
		// TODO Auto-generated method stub
		return null;
	}

}
