package com.dutypad.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.util.Constant;
import com.base.util.WriteJsonUtil;
import com.dutypad.entity.Assistant;
import com.dutypad.entity.Salaryindex;
import com.dutypad.entity.Salaryrecord;
import com.dutypad.entity.Workrecord;
import com.dutypad.interceptor.AjaxLoginFilter;
import com.dutypad.interceptor.RedirectLoginFilter;
import com.dutypad.service.AssistantService;
import com.dutypad.service.SalaryindexService;
import com.dutypad.service.SalaryrecordService;
import com.dutypad.service.WorkrecordService;

@Controller
@RequestMapping("/times")
public class TimesController {

	@Resource
	AssistantService assistantService;
	
	
	@Resource
	WorkrecordService workrecordService;
	
	@Resource
	SalaryindexService salaryindexService;
	
	@Resource
	SalaryrecordService salaryrecordService;

	@RedirectLoginFilter
	@RequestMapping(value="")
	public String redirctPad() {
		return "assistant" + "/times";
	}
	
	@AjaxLoginFilter
	@RequestMapping(value="/getMyHour")
	@ResponseBody
	public String getMyHour(HttpServletRequest request)throws IOException{
		Assistant assistant=(Assistant) request.getSession().getAttribute("assistant");
		assistant=assistantService.getByProperties("id", assistant.getId());
		String [] assiIgonreFields = new String[] {  
				"id","sid","phone","cornet","password","name","bankcard","email","power","onJob"};
		return WriteJsonUtil.writeJSON(assistant,assiIgonreFields);
		
	}
	
	@AjaxLoginFilter
	@RequestMapping(value="/getMyRecord")
	@ResponseBody
	public String getMyRecord(HttpServletRequest request)throws IOException, ParseException{
		Assistant assistant=(Assistant) request.getSession().getAttribute("assistant");
		int beginNumber=Integer.parseInt(request.getParameter("beginNumber"));
		SimpleDateFormat myDateFormat = new SimpleDateFormat("MMM dd yyyy",Locale.ENGLISH);
		Date originEndDate = myDateFormat.parse(request.getParameter("beginDate").substring(4, 15)); 
		Calendar cl = Calendar.getInstance();
		cl.setTime(originEndDate);
		cl.add(Calendar.DATE, -beginNumber);
		
		Date endDate = cl.getTime();
		cl.setTime(endDate);
		//-7设置为常量
		cl.add(Calendar.DATE, -Constant.WEEK_OFFSET);
		Date startDate = cl.getTime();
		String[] propName=new String[]{"assistant"};
		Object[] propValue=new Object[]{assistant};
		Map<String, String> sortedCondition=new HashMap<String, String>();
		sortedCondition.put("o.insertTime", "DESC");
		String betweenName="recordDate";
		 
		List<Workrecord> workrecords=workrecordService.queryByProperties(propName, propValue, sortedCondition, betweenName, startDate, endDate);
		//System.out.println(workrecords.size());
		String [] assiIgonreFields = new String[] {  
				"id","recordType","extraWork","insertTime","rewardPunish","modifyMan","assistname"};
		return WriteJsonUtil.writeJSON(workrecords,assiIgonreFields);
		
	}
	
	
	
	@AjaxLoginFilter
	@RequestMapping(value="/getThismonthHour")
	@ResponseBody
	public String getThismonthHour(HttpServletRequest request)throws IOException{
		String[] propName=new String[]{"onJob"};
		Object[] propValue=new Object[]{new Short((short)1)};
		List<Assistant> assistants=assistantService.queryByProperties(propName, propValue);
		String [] assiIgonreFields = new String[] {  
				"id","sid","phone","cornet","password","bankcard","email","power","onJob"};
		return WriteJsonUtil.writeJSON(assistants,assiIgonreFields);
		
	}
	
	@AjaxLoginFilter
	@RequestMapping(value="/salaryIndexList")
	@ResponseBody
	public String salaryIndexList(HttpServletRequest request)throws IOException{
		
		Map<String, String> sortedCondition=new HashMap<String, String>();
		sortedCondition.put("o.insertTime", "DESC");
		List<Salaryindex> salaryindexs=salaryindexService.queryByProperties(null, null, sortedCondition, null, null, null,null,null);
		HashMap<String, String> out=new HashMap<String, String>();
		for(int i=0;i<salaryindexs.size();i++){
			Salaryindex salaryindex=salaryindexs.get(i);
			out.put(salaryindex.getId().toString(), salaryindex.getiTitle());
		}
		ObjectMapper mapper= new ObjectMapper();
		 StringWriter writer = new StringWriter(); 
		String re="";
		 try{ 
			 JsonGenerator   json = new JsonFactory().createJsonGenerator(writer); 
			 mapper.writeValue(json,out); 
			     re = writer.toString(); 
			     json.close(); 
			 writer.close(); 
			     System.out.println("re========"+re); 
			 }catch(Exception e){ 
			 e.printStackTrace(); 
			 } 

		return re;
	}
	
	@AjaxLoginFilter
	@RequestMapping(value="/salaryIndexRecord")
	@ResponseBody
	public String salaryIndexRecord(HttpServletRequest request)throws IOException{
		Salaryindex salaryindex=salaryindexService.get(Integer.valueOf(request.getParameter("id")));
		String[] propName=new String[]{"salaryindex"};
		Object[] propValue=new Object[]{salaryindex};
		List<Salaryrecord> salaryrecords=salaryrecordService.queryByProperties(propName, propValue);
		List list = new ArrayList();
		for(int i=0;i<salaryrecords.size();i++){

			HashMap<String, String> out=new HashMap<String, String>();
			Salaryrecord salaryrecord=salaryrecords.get(i);
			out.put("oldsave", salaryrecord.getOldsave().toString());
			out.put("earnhour",salaryrecord.getEarnhour().toString());
			
			out.put("payhour", salaryrecord.getPayhour().toString());
			out.put("newsave",salaryrecord.getNewsave().toString());
			out.put("remarks",salaryrecord.getRemarks());
			out.put("rpall",salaryrecord.getRpall().toString());
			
			out.put("name",salaryrecord.getAssistant().getName() );
			out.put("sid", salaryrecord.getAssistant().getSid());
			list.add(out);
		}
		
		ObjectMapper mapper= new ObjectMapper();
		 StringWriter writer = new StringWriter(); 
		String re="";
		 try{ 
			 JsonGenerator   json = new JsonFactory().createJsonGenerator(writer); 
			 mapper.writeValue(json,list); 
			     re = writer.toString(); 
			     json.close(); 
			 writer.close(); 
			   //  System.out.println("re========"+re); 
			 }catch(Exception e){ 
			 e.printStackTrace(); 
			 } 

		return re;
	}
}
