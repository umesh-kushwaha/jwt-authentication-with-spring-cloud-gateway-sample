package com.umesh.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.umesh.auth.model.AuthTokenModel;
import com.umesh.auth.service.AuthService;



@RestController
public class AuthController {

	@Autowired
	private AuthService authService;

	@GetMapping(value = "generate", produces = "application/json")
	public ResponseEntity<AuthTokenModel> getJWTToken(@RequestHeader("apikey") String apiKey) {
		return ResponseEntity.ok(authService.validateApiKeyAndGetJwtToken(apiKey));
	}

}