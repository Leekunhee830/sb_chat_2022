package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptor.BeforeActionInterceptor;
import com.example.demo.interceptor.NeedLoginInterceptor;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
	
	@Autowired
	BeforeActionInterceptor beforeActionInterceptor;
	
	@Autowired
	NeedLoginInterceptor needLoginInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(beforeActionInterceptor).addPathPatterns("/**").excludePathPatterns("/resource/**")
		.excludePathPatterns("/error");
		
		registry.addInterceptor(needLoginInterceptor)
		.addPathPatterns("/usr/article/doAdd")
		.addPathPatterns("/usr/article/write")
		.addPathPatterns("/usr/article/doWrite")
		.addPathPatterns("/usr/article/modify")
		.addPathPatterns("/usr/article/doModify")
		.addPathPatterns("/usr/article/doDelete");
		
	}
}
