package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.repository.MemberRepository;
import com.example.demo.vo.Member;

@Service
public class MemberService {
	MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository=memberRepository;
	}
	
	public int join(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {
		Member oldmember=getMemberByLoginId(loginId);
		
		if(oldmember!=null) {
			return -1;
		}
		//이름 + 이메일 중복체크
		oldmember=getMemberByNameAndEmail(name,email);
		
		if(oldmember!=null) {
			return -2;
		}
		
		memberRepository.join(loginId,loginPw,name,nickname,cellphoneNo,email);
		return memberRepository.getLastInsertId();
	}

	private Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name,email);
	}

	private Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

}