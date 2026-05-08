package com.apiplatform.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apiplatform.entity.Plan;

public interface PlanRepository extends JpaRepository<Plan, Long>{
	public Optional<Plan> findByPlanName(String name);
}
