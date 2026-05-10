package com.apiplatform.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apiplatform.entity.ApiUsage;
import com.apiplatform.entity.Plan;
import com.apiplatform.entity.Subscription;
import com.apiplatform.entity.User;
import com.apiplatform.exception.LimitExceededException;
import com.apiplatform.repository.ApiUsageRepository;
import com.apiplatform.repository.SubscriptionRepository;

@Service
public class Usageservice {
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	@Autowired
	private ApiUsageRepository apiUsageRepository;
	
	public void checkAndIncrement(User user)  {
		
		Optional<Subscription> subscriptionOpt= subscriptionRepository.findByUserAndIsActiveTrue(user);
		
		if(!subscriptionOpt.isPresent())
			throw new RuntimeException("No Active Subscription for the user");
		
		Subscription subscription = subscriptionOpt.get();
		Plan plan = subscription.getPlan();
		
		String monthYear=LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
		
		Optional<ApiUsage> apiUsageOpt= apiUsageRepository.findByUserAndMonthdate(user, monthYear);
		
		ApiUsage apiUsage;
		if(!apiUsageOpt.isPresent()) {
			apiUsage = new ApiUsage();
			apiUsage.setUser(user);
			apiUsage.setMonthdate(monthYear);
			apiUsage.setRequestCount(0);
			
			apiUsageRepository.save(apiUsage);
		}
		else {
			apiUsage = apiUsageOpt.get();
		}
		
		Integer requestCount=apiUsage.getRequestCount();
		Integer monthlyLimit = plan.getMonthlyLimit();
		
		if(requestCount>=monthlyLimit)
			throw new LimitExceededException("The Montly Limit has been Exceeded");
		
		apiUsage.setRequestCount(requestCount + 1);
		apiUsageRepository.save(apiUsage);
		
		
	}

}
