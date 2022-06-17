package com.example.demo.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LeagueInfoDto;
import com.example.demo.dto.MatchInfoDto;
import com.example.demo.dto.SummonerDto;



@Service
public class LoLApiService {

	@Value("${custom.lolApi.apiKey}")
	private String API_KEY;
	BufferedReader br=null;
	
	public SummonerDto getSummoner(String SummonerName) {
		SummonerDto temp = null;

		try {
			String urlstr = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
					+ SummonerName.replace(" ", "") + "?api_key=" + API_KEY;
			
			String result=getJsonbyURL(urlstr);
			
			JSONParser jsonParser = new JSONParser();
			JSONObject summonerJo = (JSONObject) jsonParser.parse(result);

			System.out.println("json::" + summonerJo);
			int profileIconId = Integer.parseInt(summonerJo.get("profileIconId").toString());
			System.out.println("profileIconId::::" + profileIconId);
			String name = summonerJo.get("name").toString();
			String puuid = summonerJo.get("puuid").toString();
			long summonerLevel = (long) summonerJo.get("summonerLevel");
			long revisionDate = (long) summonerJo.get("revisionDate");
			String id = summonerJo.get("id").toString();
			String accountId = summonerJo.get("accountId").toString();

			temp = new SummonerDto(profileIconId, name, puuid, summonerLevel, revisionDate, id, accountId);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return temp;
	}
	
	public LeagueInfoDto[] getLeagueInfo(String id) {
		LeagueInfoDto[] leagueInfoDto = null;

		try {
			String urlstr = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/" + id + "?api_key="
					+ API_KEY;
			
			String result=getJsonbyURL(urlstr);
			
			JSONParser jsonParser = new JSONParser();
			JSONArray arr = (JSONArray) jsonParser.parse(result);
			leagueInfoDto = new LeagueInfoDto[arr.size()];
			for (int i = 0; i < arr.size(); i++) {
				JSONObject leagueInfoJo=(JSONObject) arr.get(i);
				int wins = Integer.parseInt(leagueInfoJo.get("wins").toString());
				int losses = Integer.parseInt(leagueInfoJo.get("losses").toString());
				String rank = leagueInfoJo.get("rank").toString();
				String tier = leagueInfoJo.get("tier").toString();
				String queueType = leagueInfoJo.get("queueType").toString();
				int leaguePoints =Integer.parseInt(leagueInfoJo.get("leaguePoints").toString());
				String leagueId = leagueInfoJo.get("leagueId").toString();
				leagueInfoDto[i] = new LeagueInfoDto(wins, losses, rank, tier, queueType, leaguePoints, leagueId);
			}
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return leagueInfoDto;
	}

	//경기아이디
	public String[] getMatchId(String puuid) {
		String[] matchIds = null;
		String result="";
		String urlstr = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid
				+ "/ids?start=0&count=5&api_key=" + API_KEY;
		
		try {
			result=getJsonbyURL(urlstr);
			JSONParser jsonParser = new JSONParser();
			JSONArray arr = (JSONArray) jsonParser.parse(result);
			matchIds=new String[arr.size()];
			
			for(int i=0;i<arr.size();i++) {
				matchIds[i]=arr.get(i).toString();
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return matchIds;
	}

	//경기상세정보
	public MatchInfoDto[] getMatches(String[] matchIds, String puuid) {
		MatchInfoDto[] matchInfoDtos=new MatchInfoDto[matchIds.length];
		String result="";
		int tempPuuidNum=-1;
		for(int i=0;i<matchIds.length;i++) {
			System.out.println("matchIds:::"+matchIds[i]);
		}
		try {
			for(int i=0;i<matchIds.length;i++) {
				String urlstr = "https://asia.api.riotgames.com/lol/match/v5/matches/"+matchIds[i]+"?api_key="
						+API_KEY;
				
				result=getJsonbyURL(urlstr);
				
				JSONParser jsonParser = new JSONParser();
				JSONObject k = (JSONObject) jsonParser.parse(result);
				
				//내 puuid 순번 가져오기
				JSONObject metadataJ=(JSONObject)k.get("metadata");
				JSONArray partArr = (JSONArray) metadataJ.get("participants");
				for(int j=0; j<partArr.size(); j++) {
					if(puuid.equals(partArr.get(j).toString())) {
						tempPuuidNum=j;
					}
				}
				
				if(tempPuuidNum==-1) {
					return null;
				}
				
				//내 경기 기록 가져오기
				JSONObject matchInfoJ1=(JSONObject)k.get("info");
			    String gameMode=matchInfoJ1.get("gameMode").toString();
				JSONArray arr = (JSONArray) matchInfoJ1.get("participants");
		        JSONObject matchInfoJ2 = (JSONObject)arr.get(tempPuuidNum);
		        String championName=matchInfoJ2.get("championName").toString();
		        int assists=Integer.parseInt(matchInfoJ2.get("assists").toString());
		        int deaths=Integer.parseInt(matchInfoJ2.get("deaths").toString());
		        int kiils=Integer.parseInt(matchInfoJ2.get("kills").toString());
		        int item0 = Integer.parseInt(matchInfoJ2.get("item0").toString());
				int item1 = Integer.parseInt(matchInfoJ2.get("item1").toString());
				int item2 = Integer.parseInt(matchInfoJ2.get("item2").toString());
				int item3 = Integer.parseInt(matchInfoJ2.get("item3").toString());
				int item4 = Integer.parseInt(matchInfoJ2.get("item4").toString());
				int item5 = Integer.parseInt(matchInfoJ2.get("item5").toString());
				int item6 = Integer.parseInt(matchInfoJ2.get("item6").toString());
		        boolean win=(boolean)matchInfoJ2.get("win");
				
		        System.out.println("championName:::"+championName);
		        
		        matchInfoDtos[i]=new MatchInfoDto(championName,assists,deaths,kiils,gameMode,item0
						,item1,item2,item3,item4,item5,item6,win);
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return matchInfoDtos;
	}
	
	
	private String getJsonbyURL(String urlstr) {
		String result = "";
		String line="";
		try {
			URL url= new URL(urlstr);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
	
			while ((line = br.readLine()) != null) {
				result = result + line;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	//솔로랭크
	public LeagueInfoDto getSoloRank(LeagueInfoDto[] leagueInfoDtoArr) {
		LeagueInfoDto leagueInfoDto=null;
		
		for(int i=0; i<leagueInfoDtoArr.length; i++) {
			if(leagueInfoDtoArr[i].getQueueType().equals("RANKED_SOLO_5x5")) {
				leagueInfoDto=leagueInfoDtoArr[i];
			}
		}
		
		return leagueInfoDto;
	}

	//자유랭크
	public LeagueInfoDto getFlexRank(LeagueInfoDto[] leagueInfoDtoArr) {
		LeagueInfoDto leagueInfoDto=null;
		
		for(int i=0; i<leagueInfoDtoArr.length; i++) {
			if(leagueInfoDtoArr[i].getQueueType().equals("RANKED_FLEX_SR")) {
				leagueInfoDto=leagueInfoDtoArr[i];
			}
		}
		
		return leagueInfoDto;
	}

	//랭크 승률
	public String getOdds(int wins, int losses) {
		double num=(double)wins/(double)(wins+losses) * (double)100;
		String odds=String.format("%.2f", num); 
		return odds;
	}
	

}
