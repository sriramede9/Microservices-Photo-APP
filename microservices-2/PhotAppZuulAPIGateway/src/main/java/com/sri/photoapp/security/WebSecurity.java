package com.sri.photoapp.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment env;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		
		http.headers().frameOptions().disable();
		
		// https headers to be reautharized if token expires and it creates stateless so no storing of headers or cookies
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.authorizeRequests()
		.antMatchers("/users/**")
		.permitAll()
		.antMatchers("/user-ws/users/**")
		.permitAll();
//		.antMatchers(env.getProperty("api.h2console.ui")).permitAll()
//		.antMatchers(HttpMethod.POST,env.getProperty("user.registration.url")).permitAll()
//		.antMatchers(HttpMethod.POST,env.getProperty("user.login.url"))
//		.permitAll();
//		.anyRequest()
//		.authenticated()
//		.and()
//		.addFilter(new AutorizationFilter(authenticationManager(), env));
		http.httpBasic().disable();
	}

	
	
}
