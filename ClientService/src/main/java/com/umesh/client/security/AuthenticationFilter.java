package com.umesh.client.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.umesh.client.config.PropertySource;
import com.umesh.client.exception.AuthException;



@Component

public class AuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private PropertySource propertySource;



	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			SecurityContextHolder.getContext().setAuthentication(getUser(request));
		} catch (Exception e) {
			final String msg = String.format("Authorization Failed %s", e.getMessage());
			//log.error(msg);
			throw new AuthException(msg, e);
		}
		filterChain.doFilter(request, response);
	}

	private final AuthenticationModel getUser(HttpServletRequest request) {
		final AuthenticationModel authenticationModel = new AuthenticationModel();
		authenticationModel.setAuthenticated(false);
		authenticationModel.setCredentials(request.getHeader("Authorization"));
		return authenticationModel;
	}

}
