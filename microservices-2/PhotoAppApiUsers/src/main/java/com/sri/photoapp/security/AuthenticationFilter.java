package com.sri.photoapp.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sri.photoapp.ui.controllers.UserService;
import com.sri.photoapp.ui.controllers.model.LoginRequestModel;
import com.sri.photoapp.ui.controllers.model.UserEntity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private static final int JWT_TOKEN_VALIDITY = 5000;

	private static Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

	private UserService userService;

	private Environment env;

	public AuthenticationFilter(UserService userService, Environment env) {
		super();
		this.userService = userService;
		this.env = env;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {

			log.info("in the process of attempt authentication");

			LoginRequestModel credentials = new ObjectMapper().readValue(request.getInputStream(),
					LoginRequestModel.class);

			log.info("user name and password received \t" + credentials.getEmail() + " " + credentials.getPassword());

			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
					credentials.getEmail(), credentials.getPassword(), new ArrayList<>()));
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		// genereate a jwt tocken to an authenticated user and add it to http response
		// header
		// client application will read the jwt token from http response header and use
		// the jwt token in subsequent request

		User principal = (User) authResult.getPrincipal();
		String username = principal.getUsername();

		 UserEntity userDetailsByEmail = userService.getUserDetailsByEmail(username);
		 
		String token = Jwts.builder()
		 .setSubject(userDetailsByEmail.getUserId())
		 .setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
			.signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
			.compact();
		 
		 response.addHeader("token", token);
		 response.addHeader("userId", userDetailsByEmail.getUserId());
		
	}

}
