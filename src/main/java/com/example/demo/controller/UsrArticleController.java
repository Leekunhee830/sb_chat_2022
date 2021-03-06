package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ArticleService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.ResultData;
import com.example.demo.vo.Rq;


@Controller
public class UsrArticleController {
	@Autowired
	private ArticleService articleService;

	@RequestMapping("/usr/article/write")
	public String showWrite(HttpServletRequest req,String title,String body) {
		Rq rq=(Rq)req.getAttribute("rq");
		
		return "usr/article/write";
	}
	
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(HttpServletRequest req,String title,String body) {
		Rq rq=(Rq)req.getAttribute("rq");
		
		if(Ut.empty(title)) {
			return Ut.jsHistoryBack("title(을)를 입력해주세요.");
		}
		
		if(Ut.empty(body)) {
			return Ut.jsHistoryBack("body(을)를 입력해주세요.");
		}
		
		
		ResultData<Integer> writeArticleRd=articleService.writeArticle(rq.getLoginedMemberId(),title, body);
		int id=writeArticleRd.getData1();
		
		
		return Ut.jsReplace(Ut.f("%d번 글이 생성되었습니다.", id),Ut.f("../article/detail?id=%d", id));
	}
	

	@RequestMapping("/usr/article/list")
	public String showList(Model model, HttpServletRequest req) {
		Rq rq=(Rq)req.getAttribute("rq");
		
		List<Article> articles= articleService.getForPrintArticles(rq.getLoginedMemberId());
		ResultData.from("S-1", "게시물 리스트 입니다.","articles", articles);
		
		model.addAttribute("articles",articles);
		
		return "usr/article/list";
	}
	
	@RequestMapping("/usr/article/detail")
	public String showdetail(Model model,int id,HttpServletRequest req) {
		Rq rq=(Rq)req.getAttribute("rq");
		
		Article article=articleService.getForPrintArticle(rq.getLoginedMemberId(),id);
		ResultData.from("S-1", Ut.f("%d번 게시물입니다.", id),"article", article);
		
		model.addAttribute("article",article);
		
		return "usr/article/detail";
	}
	
	@RequestMapping("/usr/article/getArticle")
	@ResponseBody
	public ResultData getArticle(int id,HttpServletRequest req) {
		Rq rq=(Rq)req.getAttribute("rq");
		
		Article article=articleService.getForPrintArticle(rq.getLoginedMemberId(),id);
		
		if(article==null) {	
			return ResultData.from("F-1", Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		return ResultData.from("S-1", Ut.f("%d번 게시물입니다.",id ),"article",article);
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req,int id) {
		Rq rq=(Rq)req.getAttribute("rq");
		
		Article article=articleService.getForPrintArticle(rq.getLoginedMemberId(),id);
		
		
		if(article==null) {
			return Ut.jsHistoryBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		ResultData actorCanDeleteRd= articleService.actorCanDelete(rq.getLoginedMemberId(),article);

		if(actorCanDeleteRd.isFail()) {
			return Ut.jsHistoryBack("권한이 없습니다.");
		}
		
		articleService.deleteArticle(id);
		
		return Ut.jsReplace(Ut.f("%d번 게시물이 삭제되었습니다.", id),"../article/list");
	}
	
	@RequestMapping("/usr/article/modify")
	public String showModify(HttpServletRequest req,int id,Model model) {
		Rq rq=(Rq)req.getAttribute("rq");
		
		Article article=articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		if(article==null) {
			return rq.historyBackJsOnView(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		ResultData actorCanModifyRd= articleService.actorCanModify(rq.getLoginedMemberId(),article);
		
		if(actorCanModifyRd.isFail()) {
			return rq.historyBackJsOnView(actorCanModifyRd.getMsg());
		}
		
		model.addAttribute("article",article);
		
		return "usr/article/modify";
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req,int id,String title,String body) {
		Rq rq=(Rq)req.getAttribute("rq");

		Article article=articleService.getForPrintArticle(rq.getLoginedMemberId(),id);
		
		if(article==null) {
			return Ut.jsHistoryBack(Ut.f("%d번 게시물이 존재하지 않습니다.", id));
		}
		
		ResultData actorCanModifyRd= articleService.actorCanModify(rq.getLoginedMemberId(),article);
		
		if(actorCanModifyRd.isFail()) {
			return Ut.jsHistoryBack(actorCanModifyRd.getMsg());
		}
		
		articleService.modifyArticle(id, title, body);
		
		return Ut.jsReplace(Ut.f("%d번 게시물이 수정 되었습니다.", id), Ut.f("../article/detail?id=%d", id));
	}

}
