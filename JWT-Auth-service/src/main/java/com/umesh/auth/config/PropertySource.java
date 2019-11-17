package com.umesh.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;



@Configuration
public class PropertySource {

	@Value("${spring.application.name}")
	private String appName;

	@Value("${app.auth.apikey.key}")
	private String appAuthApiKey;

	@Value("${app.auth.token.type}")
	private String appAuthTokenType;

	@Value("${app.auth.secret}")
	private String appAuthSecret;

	@Value("${app.auth.ttl}")
	private long appTimeToLive;

	@Value("${app.auth.header.key}")
	private String appAuthHeaderKey;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppAuthApiKey() {
		return appAuthApiKey;
	}

	public void setAppAuthApiKey(String appAuthApiKey) {
		this.appAuthApiKey = appAuthApiKey;
	}

	

	public String getAppAuthTokenType() {
		return appAuthTokenType;
	}

	public void setAppAuthTokenType(String appAuthTokenType) {
		this.appAuthTokenType = appAuthTokenType;
	}

	public String getAppAuthSecret() {
		return appAuthSecret;
	}

	public void setAppAuthSecret(String appAuthSecret) {
		this.appAuthSecret = appAuthSecret;
	}

	public long getAppTimeToLive() {
		return appTimeToLive;
	}

	public void setAppTimeToLive(long appTimeToLive) {
		this.appTimeToLive = appTimeToLive;
	}

	public String getAppAuthHeaderKey() {
		return appAuthHeaderKey;
	}

	public void setAppAuthHeaderKey(String appAuthHeaderKey) {
		this.appAuthHeaderKey = appAuthHeaderKey;
	}
	
	
}
