package com.br.spring.board.model.service;

import java.util.ArrayList;

import com.br.spring.board.model.vo.Board;
import com.br.spring.board.model.vo.Reply;
import com.br.spring.common.model.vo.PageInfo;
import com.br.spring.member.model.vo.Member;

public interface BoardService {

	int selectListCount();
	ArrayList<Board> selectList(PageInfo pi);
	
	
	int insertBoard(Board b);
	
	//3. 게시글 상세조회용 서비스
	int increseCount(int boardNo);
	Board selectBoard(int boardNo);
	
	//4. 게시글 삭제용 서비스
	int deleteBoard(int boardNo);
	
	int updateBoard(Board b);

	//6. 댓글 조회용 서비스
	ArrayList<Reply> selectReplyList(int boardNo);
	
	//7. 댓글 작성용 서비스
	int insertReply(Reply r);
	
	
}
