package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@RequestMapping("/chat")
	public ModelAndView chat() {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("chat");
		return mv;
	} 
}
