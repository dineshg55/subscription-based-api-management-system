package com.apiplatform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.apiplatform.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<User> findByUserName(String userName);
	
	public Boolean existsByUserName(String userName);
	
	public Boolean existsByEmail(String email);

}
