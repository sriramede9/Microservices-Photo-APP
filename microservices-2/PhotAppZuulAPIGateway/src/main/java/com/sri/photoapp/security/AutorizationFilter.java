package com.sri.photoapp.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class AutorizationFilter extends BasicAuthenticationFilter {

	Environment environment;

	public AutorizationFilter(AuthenticationManager authenticationManager, Environment environment) {
		super(authenticationManager);
		this.environment = environment;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String authorizationHeader = request.getHeader(environment.getProperty("Bearer"));

		if (authorizationHeader == null
				|| !authorizationHeader.startsWith(environment.getProperty("authorization.token.header.name"))) {
			chain.doFilter(request, response);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		chain.doFilter(request, response);

	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		
		String authorizationHeader = request.getHeader(environment.getProperty("authorization.token.header.name"));

		if(authorizationHeader==null) {
			return null;
		}
		
		String token = authorizationHeader.replace(environment.getProperty("authorization.token.header.prefix"), "");
		
		String userId = Jwts.parser()
				.setSigningKey(environment.getProperty("token.secret"))
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		
		if(userId == null) {
			return null;
		}
		
		return new UsernamePasswordAuthenticationToken(userId,null, new ArrayList<>());
	}

}
