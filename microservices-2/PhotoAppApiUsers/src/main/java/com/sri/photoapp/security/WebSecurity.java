package com.sri.photoapp.security;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sri.photoapp.ui.controllers.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{

	@Value("${gateway.ip}")
	String ipaddress;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private Environment env;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http
		.csrf()
		.disable();
		
		http
		.authorizeRequests()
		.antMatchers("/users/**","/h2-console/**")
		.hasIpAddress(ipaddress)
		.and()
		.addFilter(getAuthenticationFilter());		
//		.permitAll();
		
//		 to see h2-console by passing the security 
		
		http
		.headers()
		.frameOptions()
		.disable();
		
		http.httpBasic().disable();
	}

	private Filter getAuthenticationFilter() throws Exception {
		
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(userService,env);
		authenticationFilter.setAuthenticationManager(authenticationManager());
		authenticationFilter.setFilterProcessesUrl(env.getProperty("login.url.path"));
		return authenticationFilter;
	}

	@Bean
	public PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		
		auth
		.userDetailsService(userService)
		.passwordEncoder(encoder());
	}
	
	
	
}
