package com.br.spring.board.controller;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.br.spring.board.model.service.BoardService;
import com.br.spring.board.model.vo.Board;
import com.br.spring.common.model.vo.PageInfo;
import com.br.spring.common.template.FileUpload;
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
	public String insertBoard(Board b, MultipartFile upfile, HttpSession session, Model model) {
		//System.out.println(b);
		//System.out.println(upfile); => 첨부파일을 선택했든 안했든 생성된다. 단 첨부파일이 없을때 filename이 빈문자열
		if(!upfile.getOriginalFilename().equals("")) {
			//서버에 저장하는 작업
			String saveFilePath = FileUpload.saveFile(upfile, session, "resources/uploadFiles/");
			
			//원본명과 서버에 업로드된 경로(resources/xxx/xxx.xx)를 b의 changeName에 담기
			b.setOriginName(upfile.getOriginalFilename());
			b.setChangeName(saveFilePath);
		}
		
		
		int result = bService.insertBoard(b);
		
		if(result>0) {
			session.setAttribute("alertMsg", "게시글 등록에 성공하였습니다.");
			return "redirect:list.bo";
		}else {
			//에러페이지의 문구는 requestScope에 담는다 >> Model객체필요
			model.addAttribute("errorMsg", "게시글 등록에 실패하였습니다.");
			return "common/errorPage";
		}
	}
	
	@RequestMapping("detail.bo")
	public ModelAndView selectBoard(int no, ModelAndView mv) {
		//해당게시글 조회수 증가
		int result = bService.increseCount(no);
		if(result>0) {
			//상세페이지에 필요한 게시글 정보 조회해오기
			Board b = bService.selectBoard(no);
			//조회된 데이터 담아서 boardDetailView.jsp포워딩
			mv.addObject("b", b)
			  .setViewName("board/boardDetailView");
		}else {
			//에러문구 담아서 에러페이지 포워딩
			mv.addObject("errorMsg", "게시글 조회에 실패하였습니다")
			  .setViewName("common/errorPage");
		}
		return mv;
	}
	
	@RequestMapping("delete.bo")
	public String deleteBoard(int no, String filePath, HttpSession session, Model model) {
		int result = bService.deleteBoard(no);
		if(result>0) {
			//게시글 삭제 성공
			//첨부파일 여부 확인후 삭제
			//filePath : 해당 게시글의 첨부파일 저장경로 ("resources/xxx/xxx.xxx" | "")
			if(!filePath.equals("")) {
				new File(session.getServletContext().getRealPath(filePath)).delete();
			}
			session.setAttribute("alertMsg", "성공적으로 게시글이 삭제되었습니다.");
			return "redirect:list.bo";
		}else {
			//게시글 삭제실패
			//request영역에 담아서 보낸다.
			model.addAttribute("alertMsg", "게시글삭제에 실패하였습니다.");
			return "redirect:list.bo";
		}
	}
	
	@RequestMapping("updateForm.bo")
	public String updateForm(int no, Model model) {
		model.addAttribute("b", bService.selectBoard(no));
		return "board/boardUpdateForm";
	}
	
	@RequestMapping("update.bo")
	public String updateBoard(Board b, MultipartFile reupFile, HttpSession session, Model model) {
		
		//새로넘어온 첨부파일이 있을경우
		if(!reupFile.getOriginalFilename().equals("")) {
			//기존첨부파일이 있었을경우 지우기
			if(b.getOriginName() != null) {
				new File(session.getServletContext().getRealPath(b.getChangeName())).delete();
			}
			//새로운 파일 업로드
			String saveFilePath = FileUpload.saveFile(reupFile, session, "resources/uploadFiles/");
			
			//b에 새로운 첨부파일에 대한 원본명, 저장경로 이어서 담기
			b.setOriginName(reupFile.getOriginalFilename());
			b.setChangeName(saveFilePath);
		}
		
		/*
		 * 0. b에 boardNo, boardTitle, boardContent 정보는 어떤 케이스에도 담겨있음
		 * 1. New 첨부파일 X, Old 첨부파일 X
		 *    >> originName, changeName == null
		 * 2. New 첨부파일 X, Old 첨부파일 O
		 *    >> originName, changeName 둘다 기존 정보가 담겨있음
		 * 3. New 첨부파일 O, Old 첨부파일 X
		 *    >> 새로 전달된 파일 서버에 업로드
		 *    >> originaName, changeName 둘다 새로운 정보가 담겨있음
		 * 4. New 첨부파일 O, Old 첨부파일 O
		 *    >> 기존파일 삭제
		 *    >> 새로 전달된 파일 서버에 업로드
		 *    >> originaName, changeName 둘다 새로운 정보가 담겨있음
		 */
		int result = bService.updateBoard(b);
		
		if(result>0) {
			session.setAttribute("alertMsg", "게시물 수정이 완료되었습니다");
			return "redirect:detail.bo?no="+b.getBoardNo();
		}else {
			model.addAttribute("errorMsg", "게시물 수정에 실패하였습니다");
			return "common/errorPage";
		}
		
	}
	
	
}

