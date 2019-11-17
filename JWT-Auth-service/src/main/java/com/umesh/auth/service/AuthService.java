package com.umesh.auth.service;

import com.umesh.auth.model.AuthTokenModel;

public interface AuthService {

	public AuthTokenModel validateApiKeyAndGetJwtToken(String apiKey);

}
