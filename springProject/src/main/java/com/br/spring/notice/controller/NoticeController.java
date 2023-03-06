package com.br.spring.notice.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.br.spring.common.model.vo.PageInfo;
import com.br.spring.common.template.Pagination;
import com.br.spring.notice.model.service.NoticeService;
import com.br.spring.notice.model.vo.Notice;

@Controller	
public class NoticeController {
	
	@Autowired
	private NoticeService nService;
	
	@RequestMapping("list.no")
	public ModelAndView selectList(@RequestParam(value="cpage", defaultValue="1") int currentPage, ModelAndView mv) {
		
		int listCount = nService.selectListCount();
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 5, 5);
		ArrayList<Notice> list = nService.selectList(pi);
		
		mv.addObject("pi", pi)
		  .addObject("list", list)
		  .setViewName("notice/noticeListView");
		return mv;
	}
	
	@RequestMapping("enrollForm.no")
	public String enrollForm() {
		return "notice/noticeEnrollForm";
	}
	
	@RequestMapping("insert.no")
	public String insertNotice(Notice n, HttpSession session, Model model) {
		int result = nService.insertNotice(n);
		if(result > 0) { 
			session.setAttribute("alertMsg", "성공적으로 공지사항이 등록되었습니다.");
			return "redirect:list.no";
		}else { 
			model.addAttribute("errorMsg", "공지사항 등록 실패");
			return "common/errorPage";
		}
	}
	
	@RequestMapping("detail.no")
	public String selectNotice(int no, Model model) {
		Notice n = nService.selectNotice(no);
		model.addAttribute("n", n);
		return "notice/noticeDetailView";
	}
	
	@RequestMapping("delete.no")
	public String deleteNotice(int no, HttpSession session, Model model) {
		int result = nService.deleteNotice(no);
		if(result > 0) { 
			session.setAttribute("alertMsg", "공지사항 삭제가 완료되었습니다.");
			return "redirect:list.no";
		}else { 
			model.addAttribute("errorMsg", "공지사항 삭제 실패");
			return "common/errorPage";
		}
	}

}
