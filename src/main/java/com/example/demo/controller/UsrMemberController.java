package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.MemberService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Member;
import com.example.demo.vo.ResultData;
import com.example.demo.vo.Rq;

@Controller
public class UsrMemberController {
	private MemberService memberService;
	
	public UsrMemberController(MemberService memberService) {
		this.memberService=memberService;
	}
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public String doJoin(String loginId,String loginPw,String name,String nickname,String cellphoneNo,String email) {
		if(Ut.empty(loginId)) {
			return Ut.jsHistoryBack("loginId(을)를 입력해주세요.");
		}
		
		if(Ut.empty(loginPw)) {
			return Ut.jsHistoryBack("loginPw(을)를 입력해주세요.");
		}
		
		if(Ut.empty(name)) {
			return Ut.jsHistoryBack("name(을)를 입력해주세요.");
		}
		
		if(Ut.empty(nickname)) {
			return Ut.jsHistoryBack("nickname(을)를 입력해주세요.");
		}
		
		if(Ut.empty(cellphoneNo)) {
			return Ut.jsHistoryBack("cellphoneNo(을)를 입력해주세요.");
		}
		
		if(Ut.empty(email)) {
			return Ut.jsHistoryBack("email(을)를 입력해주세요.");
		}
		
		ResultData<Integer> joinRd=memberService.join(loginId,loginPw,name,nickname,cellphoneNo,email);
		
		if(joinRd.isFail()) {
			return Ut.jsHistoryBack(joinRd.getMsg());
		}
		int id=joinRd.getData1();
		
		Member member=memberService.getMemberById(id);
		
		return Ut.jsReplace(joinRd.getMsg(), "/");
	}
	
	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public String doLogout(HttpServletRequest req) {
		Rq rq=(Rq)req.getAttribute("rq");
		
		
		if(!rq.isLogined()) {
			return Ut.jsHistoryBack("이미 로그아웃되었습니다.");
		}
		
		rq.logout();
		
		return Ut.jsReplace("로그아웃되었습니다.", "/");
	}
	
	@RequestMapping("/usr/member/login")
	public String showLogin() {
		return "usr/member/login";
	}
	
	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(HttpServletRequest req,String loginId,String loginPw) {
		Rq rq=(Rq)req.getAttribute("rq");
		
		if(rq.isLogined()) {
			return Ut.jsHistoryBack("이미 로그인되었습니다.");
		}
		
		if(Ut.empty(loginId)) {
			return Ut.jsHistoryBack("loginId(을)를 입력해주세요.");
		}
		
		if(Ut.empty(loginPw)) {
			return Ut.jsHistoryBack("loginPw(을)를 입력해주세요.");
		}
		
		Member member=memberService.getMemberByLoginId(loginId);
		
		if(member==null) {
			return Ut.jsHistoryBack("존재하지 않은 로그인아이디 입니다.");
		}
		
		if(member.getLoginPw().equals(loginPw)==false) {
			return Ut.jsHistoryBack("비밀번호가 일치하지 않습니다.");
		}
		
		rq.login(member);
		
		return Ut.jsReplace(Ut.f("%s님 환영합니다.", member.getNickname()), "/");
	}
}
