package com.apiplatform.security;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apiplatform.entity.User;
import com.apiplatform.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> opt = userRepository.findByUserName(username);
		if(opt.isEmpty())
			throw new UsernameNotFoundException("No User Found");
		
		User user=opt.get();
		
		return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),Collections.emptyList());
	}

}
