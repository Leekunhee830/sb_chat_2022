package com.example.demo.util;

import com.example.demo.vo.Member;

import lombok.Getter;

public class ResultData<DT> {
	@Getter
	private String resultCode;
	@Getter
	private String msg;
	@Getter
	private String data1Name;
	@Getter
	private DT data1;
	
	private ResultData() {
		
	}
	
	public static ResultData from(String resultCode,String msg) {
		return from(resultCode, msg,null,null);
	}
	
	public static <DT> ResultData<DT> from(String resultCode,String msg,String data1Name, DT data1) {
		ResultData<DT> rd=new ResultData<DT>();
		rd.resultCode=resultCode;
		rd.msg=msg;
		rd.data1Name=data1Name;
		rd.data1=data1;
		
		return rd;
	}
	
	public boolean isSuccess() {
		return resultCode.startsWith("S-");
	}
	
	public boolean isFail() {
		return isSuccess()==false;
	}

	public static <DT> ResultData<DT> newData(ResultData rd,String data1Name, DT newData) {
		return from(rd.getResultCode(),rd.getMsg(),data1Name,newData);
	}
}
