package com.umesh.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;



@Configuration
public class PropertySource {





	@Value("${app.auth.jwt.secret}")
	private String appAuthJwtSecret;

	@Value("${app.auth.role.allowed}")
	private String appAuthRoleAllowed;
	

	public String getAppAuthSecret() {
		return appAuthJwtSecret;
	}

	public void setAppAuthSecret(String appAuthSecret) {
		this.appAuthJwtSecret = appAuthSecret;
	}

	public String getAppAuthRoleAllowed() {
		return appAuthRoleAllowed;
	}

	public void setAppAuthRoleAllowed(String appAuthRoleAllowed) {
		this.appAuthRoleAllowed = appAuthRoleAllowed;
	}
	
	

}
