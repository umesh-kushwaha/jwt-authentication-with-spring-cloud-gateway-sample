package com.umesh.client.security;

import org.springframework.security.core.GrantedAuthority;



@SuppressWarnings("serial")

public class AuthRoleModel implements GrantedAuthority {

	private String authority;

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return authority;
	}

	public AuthRoleModel(String authority) {
		super();
		this.authority = authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	
}
