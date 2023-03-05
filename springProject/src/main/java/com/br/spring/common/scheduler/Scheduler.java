package com.br.spring.common.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.br.spring.board.model.service.BoardService;

@Component
public class Scheduler {
	
	/*
	 * 특정날짜/특정시간/일정주기마다 뭔가의 작업(sql)을 실행시켜주는 스케줄링
	 * 
	 * * cron 표현식
	 * 
	 * 초 분 시 일 월 요일 연도
	 * 
	 * 초 : 0~59
	 * 분 : 0~59
	 * 시 : 0~23
	 * 일 : 1~31
	 * 월 : 1~12
	 * 요일 : 0~6(일~토) / SUN~SAT
	 * 연도 : 생략가능
	 * 
	 * ? : 설정값 없을때 (일/요일 에서만 사용가능)
	 * * : 모든조건
	 * 시작값/단위 : 시작값에서부터 해당 단위때마다 
	 * 
	 * L : 마지막 (일/요일 에서만 사용가능)
	 * W : 가장가까운평일 (일 에서만 사용가능)
	 * # : 몇주째인지 (요일 에서만 사용가능)
	 */
	@Autowired
	private BoardService bService;
	
	@Scheduled(cron="0 0/1 * * * *") // 1분마다 실행
	public void test1() {
		System.out.println("1분마다 실행됨");
	}
	
	@Scheduled(cron="0 0 0/1 * * *") // 1시간마다 실행 
	public void test2() {
		System.out.println("1시간마다 실행됨");
	}
	
	@Scheduled(cron="0 36 10 * * *")
	public void test3() {
		System.out.println("오전 10시 36분");
	}
	
	@Scheduled(cron="0 0 0 * * MON-FRI") // 월~금(평일) 00시 정각마다
	public void completeDeleteReply() {
		// 평일 00시 정각마다 현재 댓글들 중에 status가 'N'인 삭제된 댓글들을 일괄적으로 DELETE시키는 (영구삭제)
		int result = bService.completeDeleteReply();
		System.out.println(result + "행 삭제");
	}

}
