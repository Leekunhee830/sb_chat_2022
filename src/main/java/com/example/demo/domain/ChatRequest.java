package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatRequest {
	private String sessionId;
	
	public ChatRequest() {
	}
	
	public ChatRequest(String sessionId) {
		this.sessionId=sessionId;
	}
	
	
	
}
