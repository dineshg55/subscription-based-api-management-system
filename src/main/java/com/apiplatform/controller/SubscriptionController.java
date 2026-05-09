package com.apiplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apiplatform.dto.ResponseStructure;
import com.apiplatform.dto.UsageStatusResponse;
import com.apiplatform.service.SubscriptionService;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {
	
	@Autowired
	private SubscriptionService subscriptionService;
	
	@PostMapping("/{planName}")
	public ResponseEntity<ResponseStructure<String>> subscribe(@PathVariable String planName){
		  String userName = SecurityContextHolder
		            .getContext()
		            .getAuthentication()
		            .getName();
		  
		return subscriptionService.subscribe(planName,userName);
	}
	
	@GetMapping("/status")
	public ResponseEntity<ResponseStructure<UsageStatusResponse>> getStatus(){
		
		  String userName = SecurityContextHolder
		            .getContext()
		            .getAuthentication()
		            .getName();
		  
		return subscriptionService.getStatus(userName);
	}

}
