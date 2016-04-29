package com.dutypad.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.hibernate.exception.DataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.dutypad.service.SalaryrecordService;
import com.dutypad.service.WorkrecordService;

@Controller
@RequestMapping("/pad")
public class PadController {

	@Resource
	AssistantService assistantService;
	
	
	@Resource
	WorkrecordService workrecordService;
	
	
	
	@RedirectLoginFilter
	@RequestMapping(value="")
	public String redirctPad() {
		return "assistant" + "/pad";
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
	@RequestMapping(value="/getTodoList")
	@ResponseBody
	public String getTodoList(HttpServletRequest request)throws IOException{
		Date endDate=new Date();
		Calendar cl = Calendar.getInstance();
		cl.setTime(endDate);
		//-7设置为常量
		cl.add(Calendar.DATE, -Constant.WEEK_OFFSET);
		Date startDate = cl.getTime();
		String[] propName=new String[]{"todoState"};
		Object[] propValue=new Object[]{new Short((short)2)};
		Map<String, String> sortedCondition=new HashMap<String, String>();
		sortedCondition.put("o.insertTime", "DESC");
		String betweenName="recordDate";
		List<Workrecord> workrecords=workrecordService.queryByProperties(propName, propValue, sortedCondition, betweenName, startDate, endDate);
		String [] assiIgonreFields = new String[] {  
				"recordType","startTime","endTime","extraWork","remarks","todoState",
				"insertTime","hournums","rewardPunish","modifyMan"};
		return WriteJsonUtil.writeJSON(workrecords,assiIgonreFields);
	}
	
	
	@AjaxLoginFilter
	@RequestMapping(value="/getContacts")
	@ResponseBody
	public String getContacts(HttpServletRequest request)throws IOException{
		String[] propName=new String[]{"onJob"};
		Object[] propValue=new Object[]{new Short((short)1)};
		List<Assistant> assistants=assistantService.queryByProperties(propName, propValue);
		String [] assiIgonreFields = new String[] {  
				"id","password","bankcard","power","onJob",
				"workhour","savehour"};
		return WriteJsonUtil.writeJSON(assistants,assiIgonreFields);
	}
	
	@AjaxLoginFilter
	@RequestMapping(value="/getSigninRecord")
	@ResponseBody
	public String getSigninRecord(HttpServletRequest request)throws IOException, ParseException{
		//System.out.println(request.getParameter("enddate").substring(4, 15));
		String room=request.getParameter("room");
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
		String[] propName=new String[]{"classroom","recordType"};
		Object[] propValue=new Object[]{room,new Short((short)1)};
		Map<String, String> sortedCondition=new HashMap<String, String>();
		sortedCondition.put("o.insertTime", "DESC");
		String betweenName="recordDate";
		 
		List<Workrecord> workrecords=workrecordService.queryByProperties(propName, propValue, sortedCondition, betweenName, startDate, endDate);
		String [] assiIgonreFields = new String[] {  
				"id","recordType","classroom","extraWork","insertTime","rewardPunish","modifyMan"};
		return WriteJsonUtil.writeJSON(workrecords,assiIgonreFields);
		
		
		
	}
	
	
	@AjaxLoginFilter
	@RequestMapping(value="/getRPRecord")
	@ResponseBody
	public String getRPRecord(HttpServletRequest request)throws IOException, ParseException{
		//System.out.println(request.getParameter("enddate").substring(4, 15));
		int beginNumber=Integer.parseInt(request.getParameter("beginNumber"));
		SimpleDateFormat myDateFormat = new SimpleDateFormat("MMM dd yyyy",Locale.ENGLISH);
		Date originEndDate = myDateFormat.parse(request.getParameter("beginDate").substring(4, 15)); 
		Calendar cl = Calendar.getInstance();
		cl.setTime(originEndDate);
		cl.add(Calendar.DATE, -beginNumber);
		
		Date endDate = cl.getTime();
		cl.setTime(endDate);
		//-30设置为常量
		cl.add(Calendar.DATE, -Constant.MONTH_OFFSET);
		Date startDate = cl.getTime();
		String[] propName=new String[]{"recordType"};
		Object[] propValue=new Object[]{new Short((short)2)};
		Map<String, String> sortedCondition=new HashMap<String, String>();
		sortedCondition.put("o.insertTime", "DESC");
		String betweenName="recordDate";
		 
		List<Workrecord> workrecords=workrecordService.queryByProperties(propName, propValue, sortedCondition, betweenName, startDate, endDate);
		String [] assiIgonreFields = new String[] {  
				"id","recordType","startTime","endTime","classroom","extraWork",
				"todosth","todoState","insertTime","rewardPunish","modifyMan"};
		return WriteJsonUtil.writeJSON(workrecords,assiIgonreFields);
		
		
		
	}
	
	
	@AjaxLoginFilter
	@RequestMapping(value="/newWorkrecord", method = RequestMethod.POST)
	@ResponseBody
	public String  newWorkrecord(Workrecord workrecord,HttpServletRequest request)throws IOException{
		System.out.println("newWorkrecord");

		HashMap<String , Object> out=new HashMap<String,Object>();
		
		if(workrecord.getRecordType()==1){
			
			/*System.out.println(workrecord.getClassroom());
			System.out.println(workrecord.getRecordDate());
			System.out.println(workrecord.getStartTime());
			System.out.println(workrecord.getEndTime());
			System.out.println(workrecord.getExtraWork());
			System.out.println(workrecord.getRemarks());
			System.out.println(workrecord.getTodosth());
			System.out.println(workrecord.getTodoState());*/
			Date today=new Date();
			workrecord.setRecordDate(today);
			Assistant assistant=(Assistant) request.getSession().getAttribute("assistant");
			workrecord.setAssistant(assistant);
			Date insertTime=new Date();
			workrecord.setInsertTime(insertTime);
			double workhourd=(double)(workrecord.getEndTime().getTime()-workrecord.getStartTime().getTime())/(60.000*60.000*1000.000);
			
			if(workrecord.getExtraWork()){
				//这个必须改成常量！！！！！！！！！！！！！
				workhourd=workhourd*Constant.EXTRA_WORK_TIMES;
			}
			workrecord.setHournums(workhourd);
			Double oldworkhourd=assistant.getWorkhour()==null?0.000:assistant.getWorkhour();
			
			assistant.setWorkhour(oldworkhourd+workhourd);
			
			try {
				workrecordService.insertSigninRecord(workrecord,assistant);

				out.put("code", 0);
				return WriteJsonUtil.writeJSON(out);
			} catch(DataException e) {
				//应该是返回400，那么前端还是显示500服务器炸了吧,用上面那个测试一下

				out.put("code", 1);
				out.put("errorstatus","sqlError");
				return WriteJsonUtil.writeJSON(out);
			}
		}
		
		
		out.put("code", 1);
		return WriteJsonUtil.writeJSON(out);
		
	}
	
	
	@AjaxLoginFilter
	@RequestMapping(value="/solveTodo", method = RequestMethod.POST)
	@ResponseBody
	public String  solveTodo(HttpServletRequest request)throws IOException{

		HashMap<String , Object> out=new HashMap<String,Object>();
		Workrecord workrecord=workrecordService.get(Integer.valueOf(request.getParameter("id")));
		workrecord.setTodoState(new Short((short)1));
		try {
			workrecordService.update(workrecord);
			out.put("code", 0);
			return WriteJsonUtil.writeJSON(out);
		} catch(DataException e) {
			//应该是返回400，那么前端还是显示500服务器炸了吧,用上面那个测试一下
			out.put("code", 1);
			out.put("errorstatus","sqlError");
			return WriteJsonUtil.writeJSON(out);
		}
	
		
	}
	
}
