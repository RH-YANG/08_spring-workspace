package com.br.spring.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.br.spring.member.model.service.MemberService;
import com.br.spring.member.model.vo.Member;

@Controller // 해당 Controller클래스를 빈으로 등록하는 어노테이션
public class MemberController {
	
	@Autowired // DI(Dependency Injection)
	private MemberService mService;
	
	
	/*
	@RequestMapping(value="login.me") // RequestMapping어노테이션 == HandlerMapping등록
	public void loginMember() {
		System.out.println("MemberController클래스의 loginMember메소드 실행");
	}
	*/
	
	// 파라미터(요청시 전달값)를 받는 방법 (요청시 전달되는 값들 처리방법)
	/*
	 * 1) HttpServletRequest를 이용해서 전달받기 (기존의 jsp/servlet때의 방식)
	 *    메소드의 매개변수로 HttpServletRequest를 작성해두면
	 *    스프링컨테이너가 해당 메소드 호출시 자동으로 해당 객체를 생성해서 주입해줌
	 */
	/*
	@RequestMapping("login.me") 
	public void loginMember(HttpServletRequest request) {
		String userId = request.getParameter("id");
		String userPwd = request.getParameter("pwd");
		
		System.out.println("ID : " + userId);
		System.out.println("PWD : " + userPwd);
		
	}
	*/
	
	/*
	 * 2) @RequestParam 어노테이션을 이용하는 방법
	 *    request.getParameter("키"):밸류   의 역할을 대신해주는 어노테이션
	 *    
	 *    defaultValue 속성으로 기본값 설정 가능
	 */
	/*
	@RequestMapping("login.me")
	public void loginMember(@RequestParam(value="id", defaultValue="aaa") String userId, 
							@RequestParam("pwd") String userPwd) {
		
		System.out.println("ID : " + userId);
		System.out.println("PWD : " + userPwd);
		
	}
	*/
	
	/*
	 * 3) @RequestParam 어노테이션을 생략하는 방법
	 * 
	 *    ** 단, 매개변수명을 요청시전달되는키값(name속성값)과 동일하게 세팅해야됨
	 *       => 자동으로 값 주입됨
	 * 
	 */
	/*
	@RequestMapping("login.me")
	public void loginMember(String userId, String userPwd) {
		
		//System.out.println("ID : " + userId);
		//System.out.println("PWD : " + userPwd);
		
		Member m = new Member();
		m.setUserId(userId);
		m.setUserPwd(userPwd);
		
	}
	*/
	
	/*
	 * 4) 커맨드 객체 방식
	 *    다수로 넘어온 값들을 곧바로 vo객체의 필드에 담고자 할 때의 방식
	 *    
	 *    매개변수로 요청시 전달값들을 담고자하는 vo클래스 타입을 세팅
	 *    요청시 전달값의 키값(name값)을 vo클래스에 담고자하는 필드명으로 작성
	 *    
	 *    ** 반드시 키값과 담고자하는 필드명 동일해야됨!! **
	 *    
	 *    내부적으로
	 *    스프링컨테이너가 해당 객체를 "기본생성자로 생성"후 
	 *    "setter메소드"찾아서 요청시 전달값을 해당 필드에 담아줌
	 */
	@RequestMapping("login.me")
	public void loginMember(Member m) {
		//System.out.println("ID : " + m.getUserId());
		//System.out.println("PWD : " + m.getUserPwd());
		
		//MemberService mService = new MemberServiceImpl();
		// 개발자가 직접 new 라는 키워드로 객체를 생성하게되면 "결합도가 높아진다고 함"
		
		// * 결합도가 높을 경우 발생할 수 있는 문제 
		// 1. 생성하는 객체의 클래스가 수정될 경우 현재 이 클래스에서도 일일히 찾아서 수정해야되는 일이 발생
		// 2. 요청때마다 매번 새로이 객체가 생성됨 == 메모리를 빈번하게 사용하게됨 
		
		// => MemberServiceImpl 클래스를 Spring이 관리할 수 있게끔 빈으로 등록
		// => 해당 객체를 생성해서 가지고 있게됨 => 개발자는 DI로 생성된 객체를 받아서 사용만함
		Member loginUser = mService.loginMember(m);
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	

}
