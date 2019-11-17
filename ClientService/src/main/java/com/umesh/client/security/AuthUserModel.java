package com.umesh.client.security;

import java.io.Serializable;


@SuppressWarnings("serial")
public class AuthUserModel implements Serializable {

	public AuthUserModel() {
		super();
	}

	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public AuthUserModel(String userName) {
		super();
		this.userName = userName;
	}
	
	

}
