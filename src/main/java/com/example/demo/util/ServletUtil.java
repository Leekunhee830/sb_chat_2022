package com.example.demo.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ServletUtil {

	public static HttpServletRequest getHttpServletRequest() {
		RequestAttributes ra=RequestContextHolder.getRequestAttributes();
		
		if(ra==null) {
			return null;
		}
		return ((ServletRequestAttributes)ra).getRequest();
	}
	
	
	//session
	public static HttpSession getSession() {
		HttpServletRequest req=getHttpServletRequest();
		if(req!=null) {
			return req.getSession();
		}
		return null;
	}

}
