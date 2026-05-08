package com.apiplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiplatform.dto.AuthResponse;
import com.apiplatform.dto.LoginRequest;
import com.apiplatform.dto.RegisterRequest;
import com.apiplatform.dto.ResponseStructure;
import com.apiplatform.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<ResponseStructure<String>> register(@RequestBody RegisterRequest registerRequest ){
		return authService.register(registerRequest);
	}
	
	@PostMapping("/login")
	public ResponseEntity<ResponseStructure<AuthResponse>> login(@RequestBody LoginRequest loginRequest){
		return authService.login(loginRequest);
	}

}
