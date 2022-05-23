/*
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.domain.ChatRequest;
import com.example.demo.domain.ChatResponse;
import com.example.demo.service.ChatService;
import com.example.demo.util.ServletUtil;

@RestController
public class ChatController {
	
	private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
	
	@Autowired
	private ChatService chatService;
	
	//비동기 처리
	//tag :: async
	@GetMapping("/join")
	@ResponseBody
	public DeferredResult<ChatResponse> joinRequest(){
		String sessionId=ServletUtil.getSession().getId();
		logger.info(">> Join request . session id : {}",sessionId);
		
		final ChatRequest user=new ChatRequest(sessionId);
		final DeferredResult<ChatResponse> deferredResult=new DeferredResult<>(null);
		chatService.joinChatRoom(user,deferredResult);
		
		deferredResult.onCompletion(()->chatService.cancelChatRoom(user));
		deferredResult.onError((throwable)->chatService.cancelChatRoom(user));
		deferredResult.onTimeout(()->chatService.timeout(user));
		
		return deferredResult;
	}
}

*/
