package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ArticleService;
import com.example.demo.util.ResultData;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;


@Controller
public class UsrArticleController {
	@Autowired
	private ArticleService articleService;

	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public ResultData doAdd(HttpSession httpSession,String title,String body) {
		boolean isLogined=false;
		int loginedMemberId=0;
		
		if(httpSession.getAttribute("loginedMemberId")!=null) {
			isLogined=true;
			loginedMemberId=(int)httpSession.getAttribute("loginedMemberId");
		}
		
		if(!isLogined) {
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}
		
		if(Ut.empty(title)) {
			return ResultData.from("F-1", "title(을)를 입력해주세요.");
		}
		
		if(Ut.empty(body)) {
			return ResultData.from("F-2", "body(을)를 입력해주세요.");
		}
		
		
		ResultData<Integer> writeArticleRd=articleService.writeArticle(loginedMemberId,title, body);
		int id=writeArticleRd.getData1();
		
		Article article=articleService.getArticle(id);
		
		return ResultData.newData(writeArticleRd,"article", article);
	}
	

	@RequestMapping("/usr/article/list")
	public String showList(Model model) {
		List<Article> articles= articleService.getArticles();
		ResultData.from("S-1", "게시물 리스트 입니다.","articles", articles);
		
		model.addAttribute("articles",articles);
		
		return "usr/article/list";
	}
	
	@RequestMapping("/usr/article/detail")
	public String showdetail(Model model,int id) {
		Article article=articleService.getArticle(id);
		ResultData.from("S-1", Ut.f("%d번 게시물입니다.", id),"article", article);
		
		model.addAttribute("article",article);
		
		return "usr/article/detail";
	}
	
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData getArticle(int id) {
		Article article=articleService.getArticle(id);
		
		if(article==null) {	
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		return ResultData.from("S-1", Ut.f("%d번 게시물입니다.",id ),"article",article);
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public ResultData doDelete(HttpSession httpSession,int id) {
		boolean isLogined=false;
		int loginedMemberId=0;
		
		if(httpSession.getAttribute("loginedMemberId")!=null) {
			isLogined=true;
			loginedMemberId=(int)httpSession.getAttribute("loginedMemberId");
		}
		
		if(!isLogined) {
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}
		
		Article article=articleService.getArticle(id);
		
		if(article==null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		if(article.getMemberId()!=loginedMemberId) {
			return ResultData.from("F-2","권한이 없습니다.");
		}
		
		articleService.deleteArticle(id);
		
		return ResultData.from("S-1", Ut.f("%d번 게시물이 삭제되었습니다.", id),"id",id);
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public ResultData doModify(HttpSession httpSession,int id,String title,String body) {
		boolean isLogined=false;
		int loginedMemberId=0;
		
		if(httpSession.getAttribute("loginedMemberId")!=null) {
			isLogined=true;
			loginedMemberId=(int)httpSession.getAttribute("loginedMemberId");
		}
		
		if(!isLogined) {
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}
		
		Article article=articleService.getArticle(id);
		
		if(article==null) {
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		ResultData actorCanModifyRd= articleService.actorCanModify(loginedMemberId,article);
		
		if(actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}
		
		return articleService.modifyArticle(id,title,body);
	}



}
