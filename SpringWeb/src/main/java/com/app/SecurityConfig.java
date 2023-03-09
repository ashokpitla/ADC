package com.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity    
@ComponentScan("com.app")    
@PropertySource({"classpath:user.properties"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {    
	@Autowired
	private Environment env;
	@Autowired
	private BCryptPasswordEncoder pwdEncoder; 
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws	Exception {
		auth.inMemoryAuthentication().
		withUser(env.getProperty("username")).
		password(pwdEncoder.encode(env.getProperty("password"))).
		authorities(env.getProperty("role")); 
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	http.authorizeRequests()
	.antMatchers("/all").permitAll()
	.antMatchers("/admin").hasAuthority("ADMIN")
	.anyRequest().authenticated()
	.and()
	.formLogin()
	.defaultSuccessUrl("/view",true)
	.and()
	.logout()
	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	.and()
	.exceptionHandling()
	.accessDeniedPage("/denied");
	}
	}
 