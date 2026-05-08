package com.apiplatform.dto;

import lombok.Data;

@Data
public class UsageStatusResponse {
	
	private String planName;
	private Integer requestCount;
	private Integer monthlyLimit;
	private Integer remaining;

}
