package com.example.demo.vo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.demo.service.MemberService;
import com.example.demo.util.Ut;

import lombok.Getter;

public class Rq {
	@Getter
	private boolean isLogined;
	@Getter
	private int loginedMemberId;
	@Getter
	private Member loginedMember;
	
	
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;
	
	
	public Rq(HttpServletRequest req,HttpServletResponse resp, MemberService memberService) {
		this.req=req;
		this.resp=resp;
		
		this.session=req.getSession();
		boolean isLogined=false;
		int loginedMemberId=0;
		Member loginedMember=null;
		
		if(session.getAttribute("loginedMemberId")!=null) {
			isLogined=true;
			loginedMemberId=(int)session.getAttribute("loginedMemberId");
			loginedMember=memberService.getMemberById(loginedMemberId);
		}
		
		this.isLogined=isLogined;
		this.loginedMemberId=loginedMemberId;
		this.loginedMember=loginedMember;
	}

	public void printHistoryBackJs(String msg) {
		resp.setContentType("text/html; charset=UTF-8;");
		print(Ut.jsHistoryBack(msg));
	}
	
	public void print(String str) {
		try {
			resp.getWriter().append(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void login(Member member) {
		session.setAttribute("loginedMemberId", member.getId());
	}

	public void logout() {
		session.removeAttribute("loginedMemberId");
	}

	public String historyBackJsOnView(String msg) {
		req.setAttribute("historyBack", true);
		req.setAttribute("msg", msg);
		return "common/js";
	}
}
