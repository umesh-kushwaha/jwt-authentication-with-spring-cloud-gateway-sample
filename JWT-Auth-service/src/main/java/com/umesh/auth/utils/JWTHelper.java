package com.umesh.auth.utils;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTHelper {

	private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

	private JWTHelper() {
	}

	public static String createJWT(Map<String, Object> claims, String issuer, String secretKey, long ttl) {

		final JwtBuilder builder = Jwts.builder();

		final String id = (String) claims.get("userId");
		final String subject = (String) claims.get("userInfo");
		final long nowMillis = System.currentTimeMillis();
		final Date now = new Date(nowMillis);
		// TODO: PERFORMANCE
		final byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
		final Key signingKey = new SecretKeySpec(apiKeySecretBytes, SIGNATURE_ALGORITHM.getJcaName());

		builder.setId(id).setIssuedAt(now).setIssuer(issuer).setSubject(subject).addClaims(claims)
				.signWith(SIGNATURE_ALGORITHM, signingKey);
		// TODO: ELSE SCENARIO
		if (ttl >= 0) {
			long expMillis = nowMillis + ( ttl * 60 * 1000);
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		return builder.compact();
	}

}
