package com.umesh.gateway.config;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.Route.AsyncBuilder;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

	@Autowired
	private AuthFilter authFilter;

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes().route(firstServiceRouting()).build();
	}

	private final Function<PredicateSpec, AsyncBuilder> firstServiceRouting() {
		return r -> r.path("/api/v1/**")
				//
				.filters(f -> f
						//
						//.rewritePath("/api/v1/(?<segment>.*)", "/service/api/v1/${segment}")
						//
						.removeRequestHeader("Authorization")
						//
						.filter(authFilter.apply(new AuthFilterConfig())))
				//
				.uri("lb://client-service/")
				//
				.id("client-service");
	}

}
