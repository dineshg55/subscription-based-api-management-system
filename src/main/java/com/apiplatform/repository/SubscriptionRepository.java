package com.apiplatform.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.apiplatform.entity.Subscription;
import com.apiplatform.entity.User;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{
	
	public Optional<Subscription> findByUserAndIsActiveTrue(User user);

}
