package com.dutypad.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dutypad.entity.Assistant;
import com.dutypad.interceptor.AjaxLoginFilter;
import com.dutypad.interceptor.RedirectLoginFilter;
import com.dutypad.service.AssistantService;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class LoginLogoutController {
	@Resource
	private AssistantService assistantService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/login")
	public void login(HttpServletRequest request, HttpServletResponse response)throws IOException{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Assistant assistant=assistantService.getByProperties("sid", username);
		Map hashMap=new HashMap();
		if(assistant==null||assistant.getOnJob()==0){

			System.out.println("no assist");
			hashMap.put("code", 1);
			hashMap.put("error", "不存在此学号");
		}
		else if(assistant.getPassword()!=null&&password.equals(assistant.getPassword())){

			request.getSession().setAttribute("assistant",assistant);
			hashMap.put("code", 0);
		}
		else{
			hashMap.put("code", 1);
			hashMap.put("error", "密码错误");
		}
        response.setContentType("text/html;charset=utf-8");
		JsonGenerator responseJsonGenerator = new ObjectMapper().getFactory().createGenerator(response.getOutputStream(), JsonEncoding.UTF8);
		responseJsonGenerator.writeObject(hashMap);
		
	}
	
	@RedirectLoginFilter
	@RequestMapping(value="/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response)throws IOException{
		HttpSession session = request.getSession(false); 
		if(session!=null){
			session.removeAttribute("assistant");
		}
		String contextPath = request.getContextPath();
		response.sendRedirect(contextPath);
		return;
	}
}
