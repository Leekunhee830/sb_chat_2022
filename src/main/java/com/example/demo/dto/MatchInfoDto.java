package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MatchInfoDto {
	private String championName;
	private int champLevel;
	private String spell1;
	private String spell2;
	private int assists;
	private int deaths;
	private int kills;
	private String gameMode;
	private String perkIcon1;
	private String perkIcon2;
	private int item0;
	private int item1;
	private int item2;
	private int item3;
	private int item4;
	private int item5;
	private int item6;
	private String gameResult;
}
