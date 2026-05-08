package com.apiplatform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiplatform.entity.ApiUsage;
import com.apiplatform.entity.User;

public interface ApiUsageRepository extends JpaRepository<ApiUsage, Long>{
	public Optional<ApiUsage> findByUserAndMonthdate(User user, String monthdate);

}
