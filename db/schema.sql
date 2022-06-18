#DB 생성
DROP DATABASE IF EXISTS lol_chat;
CREATE DATABASE lol_chat;
USE lol_chat;

#게시물 테이블 생성
CREATE TABLE article(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL
);

#게시물 테이블에 회원정보 추가
ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER `updateDate`;

#기존 게시물의 작성자를 2번으로 지정
UPDATE article
SET memberId=2
WHERE memberId=0;

#회원 테이블 생성
CREATE TABLE `member`(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(20) NOT NULL,
    loginPw CHAR(60) NOT NULL,
    authLevel SMALLINT(2) UNSIGNED DEFAULT 3 COMMENT '권한레벨(3=일반,7=관리자)',
    `name` CHAR(20) NOT NULL,
    nickname CHAR(20) NOT NULL,
    cellphoneNo CHAR(20) NOT NULL,
    email CHAR(50) NOT NULL,
    delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '탈퇴여부(0=탈퇴전,1=탈퇴)',
    delDate DATETIME COMMENT '탈퇴날짜'
);

#게시물,테스트 데이터 생성
INSERT INTO article
SET regDate=NOW(),
updateDate=NOW(),
title='제목1',
`body`='내용1';

INSERT INTO article
SET regDate=NOW(),
updateDate=NOW(),
title='제목2',
`body`='내용2';

INSERT INTO article
SET regDate=NOW(),
updateDate=NOW(),
title='제목3',
`body`='내용3';

#회원 테스트 데이터 생성
INSERT INTO `member`
SET regDate=NOW(),
updateDate=NOW(),
loginId='admin',
loginPw='admin',
authLevel=7,
`name`='관리자',
nickname='관리자',
cellphoneNo='01011111111',
email='lkh123@test.com';

INSERT INTO `member`
SET regDate=NOW(),
updateDate=NOW(),
loginId='user1',
loginPw='user1',
`name`='사용자1',
nickname='사용자1',
cellphoneNo='01012341234',
email='usr1@test.com';

INSERT INTO `member`
SET regDate=NOW(),
updateDate=NOW(),
loginId='user2',
loginPw='user2',
`name`='사용자2',
nickname='사용자2',
cellphoneNo='01022222222',
email='user2@test.com';

#롤스펠 테이블 생성
CREATE TABLE lolspell(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    spell CHAR(20) NOT NULL,
    spellName CHAR(20) NOT NULL,
    keyNum INT(10) NOT NULL
);

#롤 스펠 등록
INSERT INTO lolspell
SET spell='SummonerBarrier',
spellName='방어막',
keyNum=21;

INSERT INTO lolspell
SET spell='SummonerBoost',
spellName='정화',
keyNum=1;

INSERT INTO lolspell
SET spell='SummonerDot',
spellName='점화',
keyNum=14;

INSERT INTO lolspell
SET spell='SummonerExhaust',
spellName='탈진',
keyNum=3;

INSERT INTO lolspell
SET spell='SummonerFlash',
spellName='점멸',
keyNum=4;

INSERT INTO lolspell
SET spell='SummonerBarrier',
spellName='방어막',
keyNum=21;

INSERT INTO lolspell
SET spell='SummonerHaste',
spellName='유체화',
keyNum=6;

INSERT INTO lolspell
SET spell='SummonerHeal',
spellName='회복',
keyNum=7;

INSERT INTO lolspell
SET spell='SummonerMana',
spellName='총명',
keyNum=13;

INSERT INTO lolspell
SET spell='SummonerPoroRecall',
spellName='왕을 향해',
keyNum=30;

INSERT INTO lolspell
SET spell='SummonerPoroThrow',
spellName='포로 던지기',
keyNum=31;

INSERT INTO lolspell
SET spell='SummonerSmite',
spellName='강타',
keyNum=11;

INSERT INTO lolspell
SET spell='SummonerSnowURFSnowball_Mark',
spellName='표식',
keyNum=39;

INSERT INTO lolspell
SET spell='SummonerSnowball',
spellName='표식',
keyNum=32;

INSERT INTO lolspell
SET spell='SummonerTeleport',
spellName='순간이동',
keyNum=12;

INSERT INTO lolspell
SET spell='Summoner_UltBookPlaceholder',
spellName='게임 시작 후 결정',
keyNum=54;

INSERT INTO lolspell
SET spell='Summoner_UltBookSmitePlaceholder',
spellName='TBD 및 공격,강타',
keyNum=55;