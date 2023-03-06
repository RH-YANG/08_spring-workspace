package com.br.spring.notice.model.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.spring.common.model.vo.PageInfo;
import com.br.spring.notice.model.dao.NoticeDao;
import com.br.spring.notice.model.vo.Notice;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private SqlSessionTemplate sqlSession;
	@Autowired
	private NoticeDao nDao;
	
	
	@Override
	public int selectListCount() {
		return nDao.selectListCount(sqlSession);
	}

	@Override
	public ArrayList<Notice> selectList(PageInfo pi) {
		return nDao.selectList(sqlSession, pi);
	}

	@Override
	public int insertNotice(Notice n) {
		return nDao.insertNotice(sqlSession, n);
	}

	@Override
	public Notice selectNotice(int noticeNo) {
		return nDao.selectNotice(sqlSession, noticeNo);
	}

	@Override
	public int deleteNotice(int noticeNo) {
		return nDao.deleteNotice(sqlSession, noticeNo);
	}

}
