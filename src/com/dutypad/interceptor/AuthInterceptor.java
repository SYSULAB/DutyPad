package com.dutypad.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;









import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.base.util.Constant;
import com.dutypad.entity.Assistant;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthInterceptor  extends HandlerInterceptorAdapter{
	@SuppressWarnings("unchecked")
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
        	RedirectLoginFilter redirectLoginFilter = ((HandlerMethod) handler).getMethodAnnotation(RedirectLoginFilter.class);
        	AjaxLoginFilter ajaxLoginFilter=((HandlerMethod) handler).getMethodAnnotation(AjaxLoginFilter.class);
        	ManageAuthFilter manageAuthFilter=((HandlerMethod) handler).getMethodAnnotation(ManageAuthFilter.class);
            
        	if(redirectLoginFilter == null && ajaxLoginFilter==null&&manageAuthFilter==null)
                return true;
            else{         
            
            	if(redirectLoginFilter!=null){
            		String requestURI = request.getRequestURI().substring(request.getRequestURI().indexOf("/",1),request.getRequestURI().length());
            		if(!"/".equals(requestURI)&&!"/login".equals(requestURI))  
                    {  
                        HttpSession session = request.getSession(false);  
                        if(session == null ||session.getAttribute("assistant")==null)  
                        {  
                        	
                            response.sendRedirect(request.getContextPath());
                            return false;  
                        }  
                        Assistant assistant=(Assistant) session.getAttribute("assistant");
                    	if(assistant.getOnJob()==0){
                    		response.sendRedirect(request.getContextPath());
                            return false;  
                    	}
                          
                    }  
            	}
            	if(ajaxLoginFilter!=null){
            		String requestURI = request.getRequestURI().substring(request.getRequestURI().indexOf("/",1),request.getRequestURI().length());
            		if(!"/".equals(requestURI)&&!"/login".equals(requestURI))  
                    {  
                        HttpSession session = request.getSession(false);  
                        if(session == null ||session.getAttribute("assistant")==null)  
                        {  
                        	
                            Map hashMap=new HashMap();
                            hashMap.put("code", 1);
                            hashMap.put("errorstatus", "redirectToLogin");
                            response.setContentType("text/html;charset=utf-8");
                    		JsonGenerator responseJsonGenerator = new ObjectMapper().getFactory().createGenerator(response.getOutputStream(), JsonEncoding.UTF8);
                    		responseJsonGenerator.writeObject(hashMap);
                            return false;  
                        }  
                        Assistant assistant=(Assistant) session.getAttribute("assistant");
                    	if(assistant.getOnJob()==0){
                    		 Map hashMap=new HashMap();
                             hashMap.put("code", 1);
                             hashMap.put("errorstatus", "redirectToLogin");
                             response.setContentType("text/html;charset=utf-8");
                     		JsonGenerator responseJsonGenerator = new ObjectMapper().getFactory().createGenerator(response.getOutputStream(), JsonEncoding.UTF8);
                     		responseJsonGenerator.writeObject(hashMap);
                             return false;  
                    	}
                          
                    }  
            	}
            	
            	if(manageAuthFilter!=null){
            			
                        HttpSession session = request.getSession(false);  
                        Assistant assistant=(Assistant) session.getAttribute("assistant");
                        if(assistant.getPower()==Constant.MANAGER_POWER||assistant.getPower()==Constant.BOSS_POWER){
                        	System.out.println(assistant.getPower());
                        	return true;
                        }
                        else{
                        	
                        	 Map hashMap=new HashMap();
                             hashMap.put("code", 1);
                             hashMap.put("errorstatus", "NoAccess");
                             response.setContentType("text/html;charset=utf-8");
                     		JsonGenerator responseJsonGenerator = new ObjectMapper().getFactory().createGenerator(response.getOutputStream(), JsonEncoding.UTF8);
                     		responseJsonGenerator.writeObject(hashMap);
                        	return false;
                        }
                     
                         
            	}
            	return true;
            }
        }
        else
            return true;   
     }
}
