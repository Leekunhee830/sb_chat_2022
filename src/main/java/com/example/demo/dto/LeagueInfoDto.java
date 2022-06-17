package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LeagueInfoDto {
	private int wins;
	private int losses;
	private String rank;
	private String tier;
	private String queueType;
	private int leaguePoints;
	private String leagueId;
}
