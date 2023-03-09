package com.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
public static void main(String[] args) {

	@SuppressWarnings("resource")
	ApplicationContext context=new AnnotationConfigApplicationContext(WebConfig.class);
	}	
}
