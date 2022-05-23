/*
package com.example.demo.service;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.context.request.async.DeferredResult;

import com.example.demo.domain.ChatRequest;
import com.example.demo.domain.ChatResponse;
import com.example.demo.domain.ChatResponse.ResponseResult;


public class ChatService {
	
	private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
	private Map<ChatRequest, DeferredResult<ChatResponse>> waitingUsers;
	private ReentrantReadWriteLock lock;
	
	@Async("asyncThreadPool")
	public void joinChatRoom(ChatRequest request,DeferredResult<ChatResponse> deferredResult) {
		logger.info("## Join chat room request. {}[{}]",Thread.currentThread().getName(),Thread.currentThread().getId());
		if(request==null || deferredResult==null) {
			return;
		}
		
		try {
			lock.writeLock().lock();
			waitingUsers.put(request, deferredResult);
		}finally {
			lock.writeLock().unlock();
			
		}
		
	}
	
	public void establishChatRoom() {
		try {
			logger.debug("Current waiting users : " + waitingUsers.size());
			lock.readLock().lock();
			if(waitingUsers.size()<2) {
				return;
			}
			
			Iterator<ChatRequest> itr=waitingUsers.keySet().iterator();
			ChatRequest user1=itr.next();
			ChatRequest user2=itr.next();
			
			//UUID = > 중복방지
			String uuid=UUID.randomUUID().toString();
			
			DeferredResult<ChatResponse> user1Result=waitingUsers.remove(user1);
			DeferredResult<ChatResponse> user2Result=waitingUsers.remove(user2);
			
			user1Result.setResult(new ChatResponse(ResponseResult.SUCCESS, uuid, user1.getSessionId()));
			user2Result.setResult(new ChatResponse(ResponseResult.SUCCESS, uuid, user2.getSessionId()));
			
		}catch (Exception e) {
			logger.warn("Exception occur while checking waiting users", e);
		}finally {
			lock.readLock().unlock();
		}
	}

	public void cancelChatRoom(ChatRequest chatRequest) {
		try {
			lock.writeLock().lock();
			setJoinResult(waitingUsers.remove(chatRequest), new ChatResponse(ResponseResult.CANCEL,null,chatRequest.getSessionId()));
		}finally {
			lock.writeLock().unlock();
		}
		
	}
	

	public void timeout(ChatRequest chatRequest) {
		try {
			lock.writeLock().lock();
            setJoinResult(waitingUsers.remove(chatRequest), new ChatResponse(ResponseResult.TIMEOUT, null, chatRequest.getSessionId()));
		}finally {
			lock.writeLock().unlock();
		}
		
	}

	private void setJoinResult(DeferredResult<ChatResponse> result,ChatResponse response) {
		if(result!=null) {
			result.setResult(response);
		}
	}
	
	
}

*/
