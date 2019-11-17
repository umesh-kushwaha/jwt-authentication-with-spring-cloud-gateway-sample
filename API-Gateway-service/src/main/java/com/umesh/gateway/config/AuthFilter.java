package com.umesh.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import com.umesh.gateway.model.AuthTokenModel;
import com.umesh.gateway.service.AuthService;

import reactor.core.publisher.Mono;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilterConfig> {

	@Autowired
	@Lazy
	private AuthService authService;

	private static Logger log = LoggerFactory.getLogger(AuthFilter.class);
	
	@SuppressWarnings("deprecation")
	@Override
	public GatewayFilter apply(final AuthFilterConfig config) {
		return (exchange, chain) -> {
			final ServerHttpRequest request = exchange.getRequest();

			final boolean authorization = request.getHeaders().containsKey("Authorization");
			log.debug("authorization " + authorization);

			if (!request.getHeaders().containsKey("apikey"))
				return this.onError(exchange, "No API KEY header", HttpStatus.UNAUTHORIZED);

			final String apiValue = request.getHeaders().get("apikey").get(0);
			if (StringUtils.isEmpty(apiValue))
				return this.onError(exchange, "Invalid API KEY", HttpStatus.UNAUTHORIZED);

			final AuthTokenModel authTokenModel = getAuthorizationToken(apiValue);
			log.debug("Gateway Auth ApiKey {} JWT {}", apiValue, authTokenModel.getToken());
			try {
				final ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
						.header("Authorization", authTokenModel.getType() + " " + authTokenModel.getToken()).build();
				return chain.filter(exchange.mutate().request(modifiedRequest).build());
			} catch (Exception e) {
				log.error(e.getMessage());
				return this.onError(exchange, "Modified Request " + e.getMessage(), HttpStatus.UNAUTHORIZED);
			}
		};
	}

	private Mono<Void> onError(final ServerWebExchange exchange, final String err, final HttpStatus httpStatus) {
		log.error("Gateway Auth Error {}", err);
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		return response.setComplete();
	}

	private AuthTokenModel getAuthorizationToken(final String apiValue) {
		log.debug("Gateway Auth ApiKey {}", apiValue);
		return authService.getJWTToken(apiValue);
	}

}
