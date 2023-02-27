package com.br.spring.ajax.controller;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AjaxController {
	
	/*
	@RequestMapping("ajax1.do")
	public void ajaxMethod1(String name, int age, HttpServletResponse response) throws IOException{
		System.out.println(name);
		System.out.println(age);
		
		//요청처리를 위한 서비스 호출
		
		//요청처리가 다 됐다는 가정하에 그 페이지를 돌려줄 응답데이터가 있을 경우
		String responseData = "응답문자열 : "+name+"은(는) "+age+"살 입니다.";
		//응답데이터가 있는 경우 문자셋 설정하기
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(responseData);
	}*/
	/*
	@ResponseBody
	@RequestMapping(value="ajax1.do", produces="text/html; charset=utf-8")
	public String ajaxMethod1(String name, int age) {
		return "응답문자열 : "+name+"은(는) "+age+"살 입니다.";
	}*/
	/*
	@RequestMapping("ajax1.do")
	public void ajaxMethod1(String name, int age, HttpServletResponse response) throws IOException {
		방법1. JSONArray로 담아서 응답
		//JSONArray jArr = new JSONArray();
		//jArr.add(name);
		//jArr.add(age);
		
		//방법2. JSONObject로 담아서 응답
		JSONObject jObj = new JSONObject();
		jObj.put("name", name);
		jObj.put("age", age);
		
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jObj);
	}*/
	
	@ResponseBody
	@RequestMapping(value="ajax1.do", produces="text/html; charset=utf-8")
	public String ajaxMethod1(String name, int age) {
		JSONObject jObj = new JSONObject();
		jObj.put("name", name);
		jObj.put("age", age);
		
		return jObj.toJSONString();
	}
	
}
