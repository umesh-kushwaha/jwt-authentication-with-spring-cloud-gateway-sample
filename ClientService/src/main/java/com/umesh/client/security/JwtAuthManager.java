package com.umesh.client.security;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.umesh.client.config.PropertySource;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Component
public class JwtAuthManager {

	@Autowired
	private PropertySource propertySource;

	public Jws<Claims> validateToken(final String token) {
		// TODO: PERFORMANCE
		final byte[] encodedSecretBytes = DatatypeConverter.parseBase64Binary(propertySource.getAppAuthSecret());
		return Jwts.parser().setSigningKey(encodedSecretBytes).parseClaimsJws(token);
	}

}
