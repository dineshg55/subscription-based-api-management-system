package com.apiplatform.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apiplatform.dto.AuthResponse;
import com.apiplatform.dto.LoginRequest;
import com.apiplatform.dto.RegisterRequest;
import com.apiplatform.dto.ResponseStructure;
import com.apiplatform.entity.User;
import com.apiplatform.exception.UserAlreadyExistsException;
import com.apiplatform.repository.UserRepository;
import com.apiplatform.security.JwtUtil;

@Service
public class AuthService {
	
//	Inject:
//		→ UserRepository
//		→ PasswordEncoder
//		→ AuthenticationManager
//		→ JwtUtil
//	Step 1 → Check if username exists
//    → existsByUsername() → if true → throw UserAlreadyExistsException
//
//Step 2 → Check if email exists
//    → existsByEmail() → if true → throw UserAlreadyExistsException
//
//Step 3 → Create new User object
//Step 4 → Set username, email from request
//Step 5 → Encode password using PasswordEncoder
//Step 6 → Save user using UserRepository
//Step 7 → Build ResponseStructure with success message
//Step 8 → Return ResponseEntity with 201 CREATED status
		
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public ResponseEntity<ResponseStructure<String>> register(RegisterRequest request){
		String username= request.getUserName();
		if(userRepository.existsByUserName(username))
			throw new UserAlreadyExistsException("Username already Exists");
		
		String email= request.getEmail();
		if(userRepository.existsByEmail(email))
			throw new UserAlreadyExistsException("Email already exists");
		
		User user= new User();
		user.setUserName(username);
		user.setEmail(email);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		
		userRepository.save(user);
		
		ResponseStructure<String> response=new ResponseStructure<String>();
		response.setStatusCode(HttpStatus.CREATED.value());
		response.setMessage("User Registeration Successful");
		response.setData("SUCCESS");
		
		return new ResponseEntity<ResponseStructure<String>>(response,HttpStatus.CREATED);
	}
	
	public ResponseEntity<ResponseStructure<AuthResponse>> login(LoginRequest request){
		
		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				request.getUserName(), 
				request.getPassword()
				));
		
		String token =jwtUtil.generateToken(request.getUserName());
		AuthResponse authResponse= new AuthResponse();
		authResponse.setToken(token);
		
		ResponseStructure<AuthResponse> response = new ResponseStructure<AuthResponse>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Login Successful");
		response.setData(authResponse);
		return new ResponseEntity<ResponseStructure<AuthResponse>>(response,HttpStatus.OK);
	}
	

}
