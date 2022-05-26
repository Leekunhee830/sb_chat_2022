package com.example.demo.util;

import com.example.demo.vo.Member;

import lombok.Getter;

public class ResultData {
	@Getter
	private String resultCode;
	@Getter
	private String msg;
	@Getter
	private Object data1;
	
	private ResultData() {
		
	}
	
	public static ResultData from(String resultCode,String msg) {
		return from(resultCode, msg,null);
	}
	
	public static ResultData from(String resultCode,String msg, Object data1) {
		ResultData rd=new ResultData();
		rd.resultCode=resultCode;
		rd.msg=msg;
		rd.data1=data1;
		
		return rd;
	}
	
	public boolean isSuccess() {
		return resultCode.startsWith("S-");
	}
	
	public boolean isFail() {
		return isSuccess()==false;
	}

	public static ResultData newData(ResultData rd, Object newData) {
		return from(rd.getResultCode(),rd.getMsg(),newData);
	}
}
