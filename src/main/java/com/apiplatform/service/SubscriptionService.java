package com.apiplatform.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apiplatform.dto.ResponseStructure;
import com.apiplatform.dto.UsageStatusResponse;
import com.apiplatform.entity.ApiUsage;
import com.apiplatform.entity.Plan;
import com.apiplatform.entity.Subscription;
import com.apiplatform.entity.User;
import com.apiplatform.repository.ApiUsageRepository;
import com.apiplatform.repository.PlanRepository;
import com.apiplatform.repository.SubscriptionRepository;
import com.apiplatform.repository.UserRepository;


@Service
public class SubscriptionService {
	
//	@Autowired:
//		→ UserRepository
//		→ PlanRepository
//		→ SubscriptionRepository
//		→ ApiUsageRepository
	
	@Autowired
	private UserRepository userRepository; 
	
	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	@Autowired
	private ApiUsageRepository apiUsageRepository;
	
	public ResponseEntity<ResponseStructure<String>> subscribe(String planName,String userName){
		
		Optional<User> userOpt = userRepository.findByUserName(userName);
		
		if(!userOpt.isPresent())
			throw new UsernameNotFoundException("No User with username "+ userName);
		
		Optional<Plan> planOpt = planRepository.findByPlanName(planName);
		
		if(!planOpt.isPresent())
			throw new RuntimeException("No Plan with PlanName "+ planName);
		
		User user = userOpt.get();
		Plan plan = planOpt.get();
		
		Optional<Subscription> subscriptionOpt= subscriptionRepository.findByUserAndIsActiveTrue(user);
		
		if(subscriptionOpt.isPresent()) {
			Subscription oldSubscription = subscriptionOpt.get();
				oldSubscription.setIsActive(false);
				subscriptionRepository.save(oldSubscription);
		}
		
			Subscription subscription=new Subscription();
			subscription.setUser(user);
			subscription.setPlan(plan);
			subscription.setIsActive(true);
			subscription.setStartdate(LocalDateTime.now());
			subscriptionRepository.save(subscription);
	
		
		ResponseStructure<String> response = new ResponseStructure<String>();
		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Subscribed Successfully");
		response.setData("SUCCESS");

		return new ResponseEntity<ResponseStructure<String>>(response,HttpStatus.OK);
		
		
	}
	
	
	public ResponseEntity<ResponseStructure<UsageStatusResponse>> getStatus(String userName){
		
		Optional<User> userOpt = userRepository.findByUserName(userName);
		
		if(!userOpt.isPresent())
			throw new UsernameNotFoundException("No User with username "+ userName);

		User user = userOpt.get();
		Optional<Subscription> subscriptionOpt = subscriptionRepository.findByUserAndIsActiveTrue(user);
		
		if(!subscriptionOpt.isPresent())
			throw new RuntimeException("No active subscription found");
		
			Subscription subscription = subscriptionOpt.get();
			Plan plan = subscription.getPlan();
			
			String monthYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
			
			Optional<ApiUsage> apiUsageOpt = apiUsageRepository.findByUserAndMonthdate(user, monthYear);
			
			Integer requestCount = 0;
			if(apiUsageOpt.isPresent())
				requestCount = apiUsageOpt.get().getRequestCount();
			
			UsageStatusResponse statusResponse = new UsageStatusResponse();
			statusResponse.setPlanName(plan.getPlanName());
			statusResponse.setMonthlyLimit(plan.getMonthlyLimit());
			statusResponse.setRequestCount(requestCount);
			statusResponse.setRemaining(plan.getMonthlyLimit()-requestCount);
			
			ResponseStructure<UsageStatusResponse> response = new ResponseStructure<UsageStatusResponse>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Usage Status");
			response.setData(statusResponse);
			
			return new ResponseEntity<ResponseStructure<UsageStatusResponse>>(response,HttpStatus.OK);
		
	}
	
	

}
