package com.umesh.client.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.umesh.client.config.PropertySource;
import com.umesh.client.exception.AuthException;
import com.umesh.client.util.TokenClaim;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@Component
public class AuthProvider implements AuthenticationProvider {

	@Autowired
	private JwtAuthManager jwtAuthManager;

	@Autowired
	private PropertySource propertySource;

	@Override
	public Authentication authenticate(final Authentication authentication) {
		final AuthenticationModel authenticationModel = (AuthenticationModel) authentication;
		try {
			doAuthorization(authenticationModel);
			SecurityContextHolder.getContext().setAuthentication(authenticationModel);
		} catch (Exception e) {
			final String msg = String.format("Authorization Failed %s", e.getMessage());
			throw new AuthException(msg, e);
		}
		return authenticationModel;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return AuthenticationModel.class.isAssignableFrom(authentication);
	}

	private final void doAuthorization(final AuthenticationModel appAuthUserModel) {
		final String cleansedToken = validateToken(appAuthUserModel.getCredentials());
		final Jws<Claims> claims = jwtAuthManager.validateToken(cleansedToken);

		appAuthUserModel.setAuthorities(getAuthorities(claims));
		appAuthUserModel.setCredentials("TBS");
		appAuthUserModel.setDetails(claims.getBody().get(TokenClaim.USER_ID.getKey(), String.class));
		appAuthUserModel.setName(claims.getBody().get(TokenClaim.USER_INFO.getKey(), String.class));
		appAuthUserModel.setPrincipal(getAuthUserModel(claims));
		appAuthUserModel.setAuthenticated(true);
	}

	private final String validateToken(final String headerValue) {
		if (ObjectUtils.isEmpty(headerValue)) throw new AuthException("Authorization Header is empty");
		
		final String token = headerValue.replace("Bearer", "");
		if (ObjectUtils.isEmpty(token)) throw new AuthException("Token is Empty");
		return token;
	}

	@SuppressWarnings("unchecked")
	private final List<AuthRoleModel> getAuthorities(Jws<Claims> claims) {
		List<String> authorities = claims.getBody().get(TokenClaim.AUTHORITIES.getKey(), List.class);
		if (ObjectUtils.isEmpty(authorities) || !authorities.contains(propertySource.getAppAuthRoleAllowed()))
			throw new AuthException("Invalid Role");
		return authorities.stream().map(AuthRoleModel::new).collect(Collectors.toList());
	}

	private final AuthUserModel getAuthUserModel(Jws<Claims> claims) {
		final AuthUserModel authUserModel = new AuthUserModel();
		authUserModel.setUserName(claims.getBody().get(TokenClaim.USER_INFO.getKey(), String.class));
		return authUserModel;
	}

}
