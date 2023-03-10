package com.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration   
@EnableWebMvc    
@Import({SecurityConfig.class}) 
@ComponentScan(basePackages="com.app")
@PropertySource({"classpath:user.properties"})
public class AppConfig {    
	@Bean
	public BCryptPasswordEncoder pwdEncoder() {
	return new BCryptPasswordEncoder();
	}
	
    @Bean    
    public InternalResourceViewResolver viewResolver() {    
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();    
        viewResolver.setViewClass(JstlView.class);    
        viewResolver.setPrefix("/WEB-INF/views/");    
        viewResolver.setSuffix(".jsp");    
        return viewResolver;    
    }    
  }    
