package com.umesh.gateway.config;

import org.springframework.cloud.gateway.support.Configurable;

public class AuthFilterConfig implements Configurable<String> {

	@Override
	public Class<String> getConfigClass() {
		return String.class;
	}

	@Override
	public String newConfig() {
		return "Authorization";
	}

}
