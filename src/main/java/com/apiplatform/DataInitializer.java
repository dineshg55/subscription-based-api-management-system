package com.apiplatform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.apiplatform.entity.Plan;
import com.apiplatform.repository.PlanRepository;

@Component
public class DataInitializer implements CommandLineRunner{
	
	@Autowired
	private PlanRepository planRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		if(planRepository.count()==0) {
			
			Plan free = new Plan();
			free.setPlanName("FREE");
			free.setMonthlyLimit(100);
			planRepository.save(free);
			
			Plan pro = new Plan();
			pro.setPlanName("PRO");
			pro.setMonthlyLimit(1000);
			planRepository.save(pro);
			
			Plan premium = new Plan();
			premium.setPlanName("PREMIUM");
			premium.setMonthlyLimit(999999);
			planRepository.save(premium);
			
			System.out.println("Plan Initialized Successfully");
		}
		
		
	}
	
}
