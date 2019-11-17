package com.umesh.client.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;


@SuppressWarnings("serial")
public class AuthenticationModel implements Authentication {

	private String name;

	private List<AuthRoleModel> authorities;

	private String credentials;

	private String details;

	private AuthUserModel principal;

	private boolean authenticated;

	@Override
	public String getName() {
		
		return name;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return authorities;
	}

	@Override
	public String getCredentials() {
		
		return credentials;
	}

	@Override
	public String getDetails() {
		
		return details;
	}

	@Override
	public AuthUserModel getPrincipal() {
		
		return principal;
	}

	@Override
	public boolean isAuthenticated() {
		
		return this.authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		
		this.authenticated = isAuthenticated;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAuthorities(List<AuthRoleModel> authorities) {
		this.authorities = authorities;
	}

	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setPrincipal(AuthUserModel principal) {
		this.principal = principal;
	}

	
}
