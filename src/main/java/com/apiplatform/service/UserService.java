package com.apiplatform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.apiplatform.entity.User;
import com.apiplatform.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public User saveUser(@RequestBody User user) {
		return userRepository.save(user);
		
	}
	
	public List<User> getAllUser(){
		return userRepository.findAll();
	}

}
