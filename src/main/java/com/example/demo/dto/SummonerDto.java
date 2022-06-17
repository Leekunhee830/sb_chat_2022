package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SummonerDto {
	private int profileIconId;
	private String name;
	private String puuid;
	private long summonerLevel;
	private long revisionDate;
	private String id;
	private String accountId;
	
}
