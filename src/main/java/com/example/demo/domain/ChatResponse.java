package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatResponse {
	private ResponseResult responseResult;
	private String chatRoomId;
	private String sessionId;
	
	public ChatResponse() {
	}
	
	public ChatResponse(ResponseResult responseResult,String chatRoomId,String sessionId) {
		this.responseResult=responseResult;
		this.chatRoomId=chatRoomId;
		this.sessionId=sessionId;
	}
	
	//enum =  상수
	public enum ResponseResult{
		SUCCESS,CANCEL,TIMEOUT;
	}
}
