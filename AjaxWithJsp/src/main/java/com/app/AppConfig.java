package com.app;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan; 
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.app")

public class AppConfig {
	
	@Bean
	public InternalResourceViewResolver ivr() {
		InternalResourceViewResolver ivr = new InternalResourceViewResolver();
		ivr.setPrefix("/WEB-INF/jsp/");
		ivr.setSuffix(".jsp");
		return ivr;
	}
}
