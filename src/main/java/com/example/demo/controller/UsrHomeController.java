package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.LeagueInfoDto;
import com.example.demo.dto.MatchInfoDto;
import com.example.demo.dto.SummonerDto;
import com.example.demo.service.LoLApiService;
import com.example.demo.vo.Rq;

import org.springframework.ui.Model;

@Controller
public class UsrHomeController {
	
	@Autowired
	LoLApiService lolApiService;
	
	@RequestMapping("/usr/home/main")
	public String showMain() {
		return "usr/home/main";
	}
	
	@RequestMapping("/")
	public String showRoot() {
		return "redirect:/usr/home/main";
	}
	
	@RequestMapping("/usr/home/doSearch")
	public String doSearch(Model model,String nickName) {
		SummonerDto temp=lolApiService.getSummoner(nickName);
		LeagueInfoDto[] leagueInfoDtoArr=lolApiService.getLeagueInfo(temp.getId());
		
		//솔로랭크 가져오기
		LeagueInfoDto soloLeagueInfo=lolApiService.getSoloRank(leagueInfoDtoArr);
		
		//자유랭크 가져오기
		LeagueInfoDto flexLeagueInfo=lolApiService.getFlexRank(leagueInfoDtoArr);
		
		//랭크 승률
		String sOdds="";
		String fOdds="";
		if(soloLeagueInfo!=null) {
			sOdds=lolApiService.getOdds(soloLeagueInfo.getWins(),soloLeagueInfo.getLosses());
		}
		if(flexLeagueInfo!=null) {
			fOdds=lolApiService.getOdds(flexLeagueInfo.getWins(),flexLeagueInfo.getLosses());
		}
		
		//경기아이디
		String[] matchIds=lolApiService.getMatchId(temp.getPuuid());
		
		//경기상세정보
		MatchInfoDto[] matchInfoDtos=lolApiService.getMatches(matchIds,temp.getPuuid());
		
		model.addAttribute("temp",temp);
		model.addAttribute("profImgURL","http://ddragon.leagueoflegends.com/cdn/12.11.1/img/profileicon/"+temp.getProfileIconId()+".png"); //프로필이미지
		model.addAttribute("leagueInfo",leagueInfoDtoArr);
		model.addAttribute("soloLeagueInfo",soloLeagueInfo);
		model.addAttribute("flexLeagueInfo",flexLeagueInfo);
		model.addAttribute("sOdds",sOdds);
		model.addAttribute("fOdds",fOdds);
		model.addAttribute("matchInfoDtos",matchInfoDtos);
		
		
		return "usr/lol/userInfo";
	}
}
