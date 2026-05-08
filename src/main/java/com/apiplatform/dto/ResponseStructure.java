package com.apiplatform.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseStructure<T> {
	
	private Integer statusCode;
	private String message;
	private T data;

}
