package com.example.demo.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LeagueInfoDto;
import com.example.demo.dto.MatchInfoDto;
import com.example.demo.dto.SummonerDto;
import com.example.demo.repository.LOLApiRepository;



@Service
public class LoLApiService {

	@Autowired
	LOLApiRepository lolApiRepository;
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
			
			int profileIconId = Integer.parseInt(summonerJo.get("profileIconId").toString());
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
		String gameMode="";
		String gameResult="";
		String spell1="";
		String spell2="";
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
				JSONObject infoJO=(JSONObject)k.get("info");
			    int queueId=Integer.parseInt(infoJO.get("queueId").toString());
				JSONArray arr = (JSONArray) infoJO.get("participants");
		        JSONObject participantJO = (JSONObject)arr.get(tempPuuidNum);
		        String championName=participantJO.get("championName").toString();
		        int champLevel=Integer.parseInt(participantJO.get("champLevel").toString());
		        int summoner1Id=Integer.parseInt(participantJO.get("summoner1Id").toString());
		        int summoner2Id=Integer.parseInt(participantJO.get("summoner2Id").toString());
		        int assists=Integer.parseInt(participantJO.get("assists").toString());
		        int deaths=Integer.parseInt(participantJO.get("deaths").toString());
		        int kiils=Integer.parseInt(participantJO.get("kills").toString());
		        int item0 = Integer.parseInt(participantJO.get("item0").toString());
				int item1 = Integer.parseInt(participantJO.get("item1").toString());
				int item2 = Integer.parseInt(participantJO.get("item2").toString());
				int item3 = Integer.parseInt(participantJO.get("item3").toString());
				int item4 = Integer.parseInt(participantJO.get("item4").toString());
				int item5 = Integer.parseInt(participantJO.get("item5").toString());
				int item6 = Integer.parseInt(participantJO.get("item6").toString());
		        boolean win=(boolean)participantJO.get("win");
		        
		        //룬정보 가져오기
		        JSONObject perksJO=(JSONObject)participantJO.get("perks");
		        JSONArray stylesArrJO = (JSONArray) perksJO.get("styles");
		        JSONObject styles1=(JSONObject)stylesArrJO.get(0);
		        JSONArray selectionsArrJO = (JSONArray)styles1.get("selections");
		        JSONObject selection1=(JSONObject)selectionsArrJO.get(0);
		        int perk1ID=Integer.parseInt(styles1.get("style").toString());
		        int perk1_subID=Integer.parseInt(selection1.get("perk").toString());
		     
		        JSONObject styles2=(JSONObject)stylesArrJO.get(1);
		        int perk2ID=Integer.parseInt(styles2.get("style").toString());
				
		        //룬아이콘 가져오기
		        String perkIcon1=getPerkIconByperkId(perk1ID,perk1_subID);
		        String perkIcon2=getPerkIconByperkId(perk2ID);
		        
		        
		        //큐아이디 이용 게임모드가져오기
		        gameMode=getGameModeByQueueId(queueId);
		        //승리여부판단
		        gameResult=getGameResultByWin(win);
		        //스펠이름가져오기
		        spell1= getSpell(summoner1Id);
		        spell2= getSpell(summoner2Id);
		        
		        matchInfoDtos[i]=new MatchInfoDto(championName,champLevel,spell1,spell2,assists,deaths,kiils,gameMode,
		        		perkIcon1,perkIcon2,item0,item1,item2,item3,item4,item5,item6,gameResult);
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
	
	//게임 모드
	private String getGameModeByQueueId(int queueId) {
		String gameMode="";
		if(queueId==400 || queueId==430) {
			gameMode="일반";
		}
		else if(queueId==420) {
			gameMode="솔랭";
		}
		else if(queueId==440) {
			gameMode="자랭";
		}
		else if(queueId==450) {
			gameMode="무작위 총력전";
		}
		else if(queueId==700) {
			gameMode="격전";
		}
		else if(queueId==800 || queueId==810 || queueId==820 || queueId==830 || queueId==840 || queueId==850) {
			gameMode="AI";
		}
		else if(queueId==900) {
			gameMode="우르프";
		}
		else if(queueId==920) {
			gameMode="포로왕";
		}
		else if(queueId==1020) {
			gameMode="단일";
		}
		else if(queueId==1300) {
			gameMode="돌넥";
		}
		else if(queueId==1400) {
			gameMode="궁주문서";
		}
		else if(queueId==2000 || queueId==2010 || queueId==2020) {
			gameMode="튜토리얼";
		}
		else {
			gameMode="오류";
		}
		
		return gameMode;
	}
	
	//승리여부
	private String getGameResultByWin(boolean win) {
		String result="";
		if(win) {
			result="승리";
		}else {
			result="패배";
		}
		
		return result;
	}
	
	//스펠이름가져오기
	private String getSpell(int spellKey) {
		String spellEnName="";
		spellEnName=lolApiRepository.getSpellBySpellKey(spellKey);
		
		if(spellEnName.equals("")) {
			spellEnName="오류";
		}
		
		return spellEnName;
	}
	
	//룬이름 가져오기
	private String getPerkIconByperkId(int userPerkId) {
		String urlstr = "https://ddragon.leagueoflegends.com/cdn/12.11.1/data/ko_KR/runesReforged.json";
		String perkicon="";
		try {
			String result=getJsonbyURL(urlstr);
			JSONParser jsonParser = new JSONParser();
			JSONArray perkArrJO = (JSONArray) jsonParser.parse(result);
			for(int i=0;i<perkArrJO.size();i++) {
				JSONObject perk=(JSONObject)perkArrJO.get(i);
				int perkId=Integer.parseInt(perk.get("id").toString());
				if(perkId==userPerkId) {
					perkicon=perk.get("icon").toString();
					return perkicon;
				}else {
					perkicon="오류";
				}
			}
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return perkicon;
	}
	
	//서브룬이름 가져오기
		private String getPerkIconByperkId(int userPerkId,int userSubPerkId) {
			String urlstr = "https://ddragon.leagueoflegends.com/cdn/12.11.1/data/ko_KR/runesReforged.json";
			String perkIcon="";
			try {
				String result=getJsonbyURL(urlstr);
				JSONParser jsonParser = new JSONParser();
				JSONArray perkArrJO = (JSONArray) jsonParser.parse(result);
				for(int i=0;i<perkArrJO.size();i++) {
					JSONObject perk=(JSONObject)perkArrJO.get(i);
					int perkId=Integer.parseInt(perk.get("id").toString());
					if(perkId==userPerkId) {
						JSONArray slotsArr=(JSONArray)perk.get("slots");
						for(int j=0;j<slotsArr.size();j++) {
							JSONObject slot=(JSONObject)slotsArr.get(j);
							JSONArray rune= (JSONArray)slot.get("runes");
							for(int q=0;q<rune.size();q++) {
								JSONObject runeJO=(JSONObject)rune.get(q);
								int runeId=Integer.parseInt(runeJO.get("id").toString());
								if(runeId==userSubPerkId) {
									perkIcon=runeJO.get("icon").toString();
									return perkIcon;
								}else {
									perkIcon="오류";
								}
									
							}
						}
					}else {
						perkIcon="오류";
					}
				}
				
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return perkIcon;
		}
}
