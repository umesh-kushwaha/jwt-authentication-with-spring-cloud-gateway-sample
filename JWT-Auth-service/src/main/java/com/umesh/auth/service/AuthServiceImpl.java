package com.umesh.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.umesh.auth.config.PropertySource;
import com.umesh.auth.exception.AuthException;
import com.umesh.auth.model.AuthTokenModel;
import com.umesh.auth.repo.DataStore;
import com.umesh.auth.utils.JWTHelper;
import com.umesh.auth.utils.TokenClaim;



@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private DataStore dataStore;

	@Autowired
	private PropertySource propertySource;

	@Override
	public AuthTokenModel validateApiKeyAndGetJwtToken(final String apiKey) {
		try {
			final String userId = validateApiKeyAndGetUserId(apiKey);
			final Map<String, Object> claims = getUserInfo(userId);
			final String jwtTokenValue = JWTHelper.createJWT(claims, propertySource.getAppName(),
					propertySource.getAppAuthSecret(), propertySource.getAppTimeToLive());
			return getTokenModel(jwtTokenValue);
		} catch (Exception e) {
			throw new AuthException("Unauthorized API key : " + apiKey, e);
		}
	}

	private final String validateApiKeyAndGetUserId(final String apiKey) {
		return Optional.ofNullable(dataStore.getUserIdForApikey(apiKey))
				.orElseThrow(() -> new AuthException("InValid API Key"));
	}

	private final Map<String, Object> getUserInfo(final String userId) {
		final Map<String, Object> claims = new HashMap<>();
		final String userInfo = dataStore.getUserInfo(userId);
		final String userRole = dataStore.getUserRole(userId);
		final List<String> authorities = new ArrayList<>();
		authorities.add(userRole);
		claims.put(TokenClaim.USER_ID.getKey(), userId);
		claims.put(TokenClaim.USER_INFO.getKey(), userInfo);
		claims.put(TokenClaim.AUTHORITIES.getKey(), authorities);
		return claims;
	}

	private final AuthTokenModel getTokenModel(final String jwtTokenValue) {
		final AuthTokenModel tokenModel = new AuthTokenModel();
		tokenModel.setType(propertySource.getAppAuthTokenType());
		tokenModel.setToken(jwtTokenValue);
		return tokenModel;
	}

}