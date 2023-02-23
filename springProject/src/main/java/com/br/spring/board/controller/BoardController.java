package com.br.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.br.spring.board.model.service.BoardService;
import com.br.spring.board.model.vo.Board;
import com.br.spring.common.model.vo.PageInfo;
import com.br.spring.common.template.Pagination;

@Controller
public class BoardController {

	@Autowired
	private BoardService bService;
	
	/*
	@RequestMapping("list.bo")
	public String selsctList(@RequestParam(value="cpage", defaultValue="1") int currentPage, Model model) {
		
		int listCount = bService.selectListCount();
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 5);
		ArrayList<Board> list = bService.selectList(pi);
		model.addAttribute("pi", pi);
		model.addAttribute("list", list);
		
		//포워딩할 응답뷰
		return "board/boardListView";
	} */
	
	//ModelAndView 이용하기
	@RequestMapping("list.bo")
	public ModelAndView selsctList(@RequestParam(value="cpage", defaultValue="1") int currentPage, ModelAndView mv) {
		
		int listCount = bService.selectListCount();
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 5);
		ArrayList<Board> list = bService.selectList(pi);

		/*
		mv.addObject("pi", pi);
		mv.addObject("list", list);
		mv.setViewName("board/boardListView");
		*/
		
		mv.addObject("pi", pi)
		  .addObject("list", list)
		  .setViewName("board/boardListView");
		
		return mv;
	}
	
	@RequestMapping("enrollForm.bo")
	public String enrollForm() {
		return "board/boardEnrollForm";
	}
	
	@RequestMapping("insert.bo")
	public void insertBoard(Board b, MultipartFile upfile, HttpSession session) {
		//System.out.println(b);
		//System.out.println(upfile); => 첨부파일을 선택했든 안했든 생성된다. 단 첨부파일이 없을때 filename이 빈문자열
		
		if(!upfile.getOriginalFilename().equals("")) {
			//파일명 수정 작업
			String originName = upfile.getOriginalFilename();
			String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			int ranNum = (int)(Math.random()*90000 + 10000);
			String ext = originName.substring(originName.lastIndexOf("."));
			String changeName = currentTime + ranNum + ext;
			
			//업로드할 폴더의 물리적인 경로
			String savePath = session.getServletContext().getRealPath("resources/uploadFiles/");
			
			
			try {
				//Board객체에 데이터 넣기
				upfile.transferTo(new File(savePath+changeName));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
			
			
			
		}
		
	}
}
