package com.umesh.client.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("first")
public class FirstController {

	

	@GetMapping("test")
	public ResponseEntity<String> readFirst(Authentication authentication) {
		
		return ResponseEntity.ok("{\"status\":\"access granted\"}");
	}

}