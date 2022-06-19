<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="pageTitle" value="소환사 정보" />
<%@ include file="../common/head.jspf"%>


<div class="container mx-auto px-44 mt-14">

  <div>
    <div class="flex h-40">
      <div class="w-32 h-32">
        <img class="rounded-xl" src="${profImgURL}" alt="프로필이미지" />
        <div class="text-center">
          <div class="text-lg font-extrabold badge">${temp.summonerLevel}</div>
        </div>
      </div>
      <div class="ml-3">
        <div>${soloLeagueInfo.tier}&nbsp;${soloLeagueInfo.rank}</div>
        <div class="text-3xl font-extrabold">${temp.name}</div>
      </div>
    </div>
    <hr />
  </div>

  <div class="flex">
    <!-- 왼쪽박스 -->
    <div class="w-full">
    
      <div class="w-96 h-36 bg-gray-200 rounded-xl px-1.5">
        <div class="text-center mt-2.5">솔로랭크</div>
        <c:choose>
          <c:when test="${not empty soloLeagueInfo}">
            <div class="flex">
              <div class="w-24 h-24">
                <img src="/resource/img/emblems/Emblem_${soloLeagueInfo.tier}.png" alt="솔로엠블렘이미지" />
              </div>
              <div class="mt-7 ml-2">
                <div class="text-2xl font-extrabold">${soloLeagueInfo.tier}&nbsp;${soloLeagueInfo.rank}</div>
                <div class="text-gray-500">${soloLeagueInfo.leaguePoints}LP</div>
              </div>
              <div class="ml-auto mt-8 text-gray-500">
                <div>승:${soloLeagueInfo.wins}&nbsp;패:${soloLeagueInfo.losses}</div>
                <div>승률 ${sOdds}%</div>
              </div>
            </div>
          </c:when>
          <c:otherwise>
            <div class="h-full text-center mt-9">Unranked</div>
          </c:otherwise>
        </c:choose>
      </div>
      
      <div class="w-96 h-36 bg-gray-200 rounded-xl px-1.5">
        <div class="text-center mt-2.5">자유랭크</div>

        <c:choose>
          <c:when test="${not empty flexLeagueInfo}">
            <div class="flex">
              <div class="w-24 h-24">
                <img src="/resource/img/emblems/Emblem_${flexLeagueInfo.tier}.png" alt="솔로엠블렘이미지" />
              </div>
              <div class="mt-7 ml-1">
                <div class="text-2xl font-extrabold">${flexLeagueInfo.tier}&nbsp;${flexLeagueInfo.rank}</div>
                <div class="text-gray-500">${flexLeagueInfo.leaguePoints}LP</div>
              </div>
              <div class="ml-auto mt-8 text-gray-500">
                <div>승:${flexLeagueInfo.wins}&nbsp;패:${flexLeagueInfo.losses}</div>
                <div>승률 ${fOdds}%</div>
              </div>
            </div>
          </c:when>
          <c:otherwise>
            <div class="h-full text-center mt-9">Unranked</div>
          </c:otherwise>
        </c:choose>

      </div>
    </div>


    <!-- 오른쪽박스 -->
    <div class="w-full">
      <c:choose>
        <c:when test="${not empty matchInfoDtos}">
          <c:forEach var="matchInfoDto" items="${matchInfoDtos}">
            <!-- 매치 정보시작 -->
            <div class="mt-2.5 w-full h-24 flex px-2 rounded-xl ${matchInfoDto.gameResult}">
              <!-- 승패,게임모드 -->
              <div class="w-24 h-full table">
                <div class="table-cell align-middle">
                  <div class="text-center font-extrabold">${matchInfoDto.gameMode }</div>
                  <hr class="my-2 text-gray-600">
                  <div class="text-center font-extrabold">${matchInfoDto.gameResult }</div>
                </div>
              </div>
              
              <!-- 챔프이미지,레벨 -->
              <div class="ml-2 relative">
                <div class="w-16 h-16 mt-3">
                  <img class="rounded-full" src="https://ddragon.leagueoflegends.com/cdn/12.11.1/img/champion/${matchInfoDto.championName}.png" alt="챔프이미지" />              
                </div>
                <div class="badge absolute top-14 left-10">${matchInfoDto.champLevel}</div>
              </div>
              
              <!-- 룬,스펠 -->
              <div class="ml-2 mt-3">
                <div class="flex">
                  <div class="w-8 h-8 bg-black rounded-full">
                    <img src="https://ddragon.leagueoflegends.com/cdn/img/${matchInfoDto.perkIcon1}" alt="룬이미지1" />
                  </div>
                  <div class="w-8 h-8 rounded-full">
                    <img src="https://ddragon.leagueoflegends.com/cdn/img/${matchInfoDto.perkIcon2}" alt="룬이미지2" />
                  </div>
                </div>
                <div class="flex">
                  <div class="w-8 h-8">
                    <img src="https://ddragon.leagueoflegends.com/cdn/12.11.1/img/spell/${matchInfoDto.spell1}.png" alt="스펠이미지1" />
                  </div>
                  <div class="w-8 h-8">
                    <img src="https://ddragon.leagueoflegends.com/cdn/12.11.1/img/spell/${matchInfoDto.spell2}.png" alt="스펠이미지2" />
                  </div>
                </div>
              </div>
              
            </div>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <div>경기 기록이 없습니다.</div>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>

<!-- 연습 -->
<div class="mt-14">
  ${temp}
  <img alt="프로필이미지" src="${profImgURL}">


  <div>
    <c:forEach var="leagueInfo" items="${leagueInfo}">
      ${leagueInfo}
      <div>
        <img alt="티어아이콘" src="/resource/img/emblems/Emblem_${leagueInfo.tier}.png">
      </div>
    </c:forEach>
  </div>
  
  <div>
    <c:forEach var="matchInfoDto" items="${matchInfoDtos}">
      ${matchInfoDto}
      
    </c:forEach>
  </div>
</div>

<script type="text/javascript">
$(document).ready(function() {
	 $('.승리').css('backgroundColor', '#a6e3e9');
	 $('.패배').css('backgroundColor', '#f0bcc6');
});
	//element.getElementsByClassName('.승리').style.backgroundColor = 'green';
	//element.getElementsByClassName('.패배').style.backgroundColor = 'red';
</script>

<script type="text/javascript" src="/resource/userInfoJs.js" defer="defer"></script>
<%@ include file="../common/foot.jspf"%>