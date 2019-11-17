package com.umesh.gateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.umesh.gateway.config.FeignConfig;
import com.umesh.gateway.model.AuthTokenModel;



@FeignClient(name = "${auth.serviceid}", configuration = FeignConfig.class)
public interface AuthService {

	@GetMapping(value = "/auth/generate")
	public AuthTokenModel getJWTToken(@RequestHeader("apikey") String apiKey);

}