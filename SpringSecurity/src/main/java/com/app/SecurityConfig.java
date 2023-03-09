package com.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity    
@ComponentScan("com.app")   
@PropertySource({"classpath:user.properties"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {    
@Autowired
private Environment env;
@SuppressWarnings("deprecation")
@Bean    
public UserDetailsService userDetailsService() {    
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();    
    manager.createUser(User.withDefaultPasswordEncoder()  
    .username(env.getProperty("username")).password(env.getProperty("password")).roles(env.getProperty("role")).build());    
    return manager;    
}    
@Override    
protected void configure(HttpSecurity http) throws Exception {    
        
      http.authorizeRequests().  
      antMatchers("/index", "/user","/").permitAll()  
      .antMatchers("/admin").authenticated()  
      .and()  
      .formLogin()  
      .loginPage("/login")  
      .and()  
      .logout()  
      .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));  
}    
}   