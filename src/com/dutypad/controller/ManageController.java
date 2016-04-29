package com.dutypad.controller;

import java.io.IOException;
import java.io.StringWriter;
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
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonFactory;
import org.hibernate.exception.DataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.util.Constant;
import com.base.util.WriteJsonUtil;
import com.dutypad.entity.Assistant;
import com.dutypad.entity.Lastwage;
import com.dutypad.entity.Salaryindex;
import com.dutypad.entity.Salaryrecord;
import com.dutypad.entity.Workrecord;
import com.dutypad.interceptor.AjaxLoginFilter;
import com.dutypad.interceptor.ManageAuthFilter;
import com.dutypad.interceptor.RedirectLoginFilter;
import com.dutypad.service.AssistantService;
import com.dutypad.service.LastwageService;
import com.dutypad.service.SalaryindexService;
import com.dutypad.service.SalaryrecordService;
import com.dutypad.service.WorkrecordService;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

@Controller
@RequestMapping("/manage")
public class ManageController {

	@Resource
	AssistantService assistantService;
	
	
	@Resource
	WorkrecordService workrecordService;
	
	@Resource
	LastwageService lastwageService;
	
	@Resource
	SalaryindexService salaryindexService;
	
	@Resource
	SalaryrecordService salaryrecordService;
	
	@SuppressWarnings("unchecked")
	@ManageAuthFilter
	@AjaxLoginFilter
	@RequestMapping(value="/Entry")
	public void manageEntry(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException {
		HashMap hashMap=new HashMap();
        hashMap.put("code", 0);
        response.setContentType("text/html;charset=utf-8");
 		JsonGenerator responseJsonGenerator;
		responseJsonGenerator = new ObjectMapper().getJsonFactory().createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
		
 		responseJsonGenerator.writeObject(hashMap);
	}
	
	
	@ManageAuthFilter
	@RedirectLoginFilter
	@RequestMapping(value="")
	public String redirctPad() {
		return "assistant" + "/manage";
	}
	
	
	@SuppressWarnings("unchecked")
	@ManageAuthFilter
	@AjaxLoginFilter
	@RequestMapping(value="/getAssistList")
	@ResponseBody
	public void getAssistList(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException {
		HashMap hashMap=new HashMap();
       // hashMap.put("code", 0);
		String[] propName=new String[]{"onJob"};
		Object[] propValue=new Object[]{new Short((short)1)};
		List<Assistant> assistants=assistantService.queryByProperties(propName, propValue);
		for(int i=0;i<assistants.size();i++){
			hashMap.put(assistants.get(i).getId(), assistants.get(i).getName());
		}
        response.setContentType("text/html;charset=utf-8");
 		JsonGenerator responseJsonGenerator;
		responseJsonGenerator = new ObjectMapper().getJsonFactory().createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
		
 		responseJsonGenerator.writeObject(hashMap);
	}
	
	@SuppressWarnings("unchecked")
	@ManageAuthFilter
	@AjaxLoginFilter
	@RequestMapping(value="/getAssistRecord")
	@ResponseBody
	public String getAssistRecord(HttpServletRequest request) throws IOException {
		Integer id=Integer.valueOf(request.getParameter("id"));
		
		Date endDate =new Date();
		
		Lastwage lastwage= lastwageService.getByProperties("id", 1);
		Date startDate=lastwage.getInsertTime();
		Assistant assistant=assistantService.get(id);
		if(assistant!=null&&assistant.getOnJob()!=0){
			String[] propName=new String[]{"assistant"};
			Object[] propValue=new Object[]{assistant};
			Map<String, String> sortedCondition=new HashMap<String, String>();
			sortedCondition.put("o.insertTime", "DESC");
			String betweenName="insertTime";
			 
			List<Workrecord> workrecords=workrecordService.queryByProperties(propName, propValue, sortedCondition, betweenName, startDate, endDate);
			//System.out.println(workrecords.size());
			String [] assiIgonreFields = new String[] {  
					"id","recordType","extraWork","insertTime","modifyMan","assistname"};
			return WriteJsonUtil.writeJSON(workrecords,assiIgonreFields);
		}
		return WriteJsonUtil.writeJSON(null);
		
	}
	
	
	@SuppressWarnings("unchecked")
	@ManageAuthFilter
	@AjaxLoginFilter
	@RequestMapping(value="/newRPRecord")
	@ResponseBody
	public String newRPRecord(Workrecord workrecord,HttpServletRequest request) throws IOException {

		HashMap<String , Object> out=new HashMap<String,Object>();
		
		if(workrecord.getRecordType()==2){
			
			/*System.out.println(workrecord.getClassroom());
			System.out.println(workrecord.getRecordDate());
			System.out.println(workrecord.getStartTime());
			System.out.println(workrecord.getEndTime());
			System.out.println(workrecord.getExtraWork());
			System.out.println(workrecord.getRemarks());
			System.out.println(workrecord.getTodosth());
			System.out.println(workrecord.getTodoState());*/
			Integer affectedid =Integer.valueOf(request.getParameter("affectedid"));
			Assistant affectedAssistant=assistantService.get(affectedid);
			workrecord.setAssistant(affectedAssistant);
			Date today=new Date();
			workrecord.setRecordDate(today);
			Assistant assistant=(Assistant) request.getSession().getAttribute("assistant");
			//workrecord.setAssistant(assistant);
			workrecord.setModifyMan(assistant.getName());
			
			Date insertTime=new Date();
			workrecord.setInsertTime(insertTime);
			workrecord.setHournums(workrecord.getRewardPunish()*workrecord.getHournums());
			
			Double oldworkhourd=affectedAssistant.getWorkhour()==null?0.000:affectedAssistant.getWorkhour();
			affectedAssistant.setWorkhour(oldworkhourd+workrecord.getHournums());
			System.out.println(affectedAssistant.getWorkhour());
			try {
				workrecordService.insertRPRecord(workrecord,affectedAssistant);
				out.put("code", 0);
				return WriteJsonUtil.writeJSON(out);
			} catch(DataException e) {
				out.put("code", 1);
				out.put("errorstatus","sqlError");
				return WriteJsonUtil.writeJSON(out);
				
				
			}
		}
		
		out.put("code", 1);
		return WriteJsonUtil.writeJSON(out);
	}
	
	@ManageAuthFilter
	@AjaxLoginFilter
	@RequestMapping(value="/getSelectedAssist")
	@ResponseBody
	public String getSelectedAssist(HttpServletRequest request) throws IOException {
		Integer id=Integer.valueOf(request.getParameter("id"));
		
		Assistant assistant=assistantService.get(id);
		
		String [] assiIgonreFields = new String[] {  
				"id","onJob","workhour","savehour"};
		return WriteJsonUtil.writeJSON(assistant,assiIgonreFields);
	}
	
	@ManageAuthFilter
	@AjaxLoginFilter
	@RequestMapping(value="/modifySelectedAssist")
	@ResponseBody
	public  String  modifySelectedAssist(HttpServletRequest request) throws IOException {
		Assistant assistant=assistantService.get(Integer.valueOf(request.getParameter("id")));
		HashMap<String , Object> out=new HashMap<String,Object>();
		
		assistant.setSid(request.getParameter("sid"));
		assistant.setName(request.getParameter("name"));
		assistant.setPassword(request.getParameter("password"));
		assistant.setPhone(request.getParameter("phone"));
		assistant.setCornet(request.getParameter("cornet"));
		assistant.setEmail(request.getParameter("email"));
		assistant.setBankcard(request.getParameter("bankcard"));
		assistant.setPower(new Short(request.getParameter("power")));
		
		try {
			assistantService.update(assistant);
			out.put("code", 0);
			return WriteJsonUtil.writeJSON(out);
		} catch(DataException e) {
			out.put("code", 1);
			out.put("errorstatus","sqlError");
			return WriteJsonUtil.writeJSON(out);
		}
	}
	
	@ManageAuthFilter
	@AjaxLoginFilter
	@RequestMapping(value="/deleteSelectedAssist")
	@ResponseBody
	public String  deleteSelectedAssist(HttpServletRequest request) throws IOException {
		Assistant assistant=assistantService.get(Integer.valueOf(request.getParameter("id")));
		assistant.setOnJob(new Short((short)0));
		HashMap<String , Object> out=new HashMap<String,Object>();
		try {
			assistantService.update(assistant);
			out.put("code", 0);
			return WriteJsonUtil.writeJSON(out);
		} catch(DataException e) {
			out.put("code", 1);
			out.put("errorstatus","sqlError");
			return WriteJsonUtil.writeJSON(out);
		}
	}
	
	@ManageAuthFilter
	@AjaxLoginFilter
	@RequestMapping(value="/newdAssist")
	@ResponseBody
	public  String  newdAssist(HttpServletRequest request,Assistant assistant) throws IOException {
		assistant.setOnJob(new Short((short)1));
		assistant.setSavehour(0.00);
		assistant.setWorkhour(0.00);
		HashMap<String , Object> out=new HashMap<String,Object>();
		
		try {
			assistantService.persist(assistant);
			out.put("code", 0);
			return WriteJsonUtil.writeJSON(out);
		} catch(DataException e) {
			out.put("code", 1);
			out.put("errorstatus","sqlError");
			return WriteJsonUtil.writeJSON(out);
		}
	}
	
	
	@ManageAuthFilter
	@AjaxLoginFilter
	@RequestMapping(value="/getThemonthHour")
	@ResponseBody
	public String getThemonthHour(HttpServletRequest request) throws IOException {
		 
		 String[] propName=new String[]{"onJob"};
			Object[] propValue=new Object[]{new Short((short)1)};
			List<Assistant> assistants=assistantService.queryByProperties(propName, propValue);
		
		List list = new ArrayList();
		for(int i=0;i<assistants.size();i++){
			Assistant assist=assistants.get(i);
			HashMap<String, String> json=new HashMap<String, String>();
			Double savehour=assist.getSavehour()==null?0.00:assist.getSavehour();
			Double workhour=assist.getWorkhour()==null?0.00:assist.getWorkhour();
			json.put("oldsave", savehour.toString());
			json.put("earnhour",workhour.toString());
			double payhourd=savehour+workhour;
			double payhour=(payhourd)>=Constant.PER_MONTH_PAY?Constant.PER_MONTH_PAY:payhourd;
			int payhouri=(int)payhour;
			payhour=(double)payhouri;
			Double newsave= savehour+workhour-payhour;
			
			json.put("payhour", payhour+"");
			json.put("newsave",newsave.toString());
			
			json.put("name",assist.getName());
			json.put("sid", assist.getSid());
			propName=new String[]{"assistant","recordType"};
			propValue=new Object[]{assist,new Short((short)2)};
			Date endDate =new Date();
			
			Lastwage lastwage= lastwageService.getByProperties("id", 1);
			Date startDate=lastwage.getInsertTime();
			Map<String, String> sortedCondition=new HashMap<String, String>();
			sortedCondition.put("o.insertTime", "DESC");
			String betweenName="insertTime";
			double rpall=0;
			StringBuffer remarks=new StringBuffer();
			List<Workrecord> workrecords=workrecordService.queryByProperties(propName, propValue, sortedCondition, betweenName, startDate, endDate);
			for(int j=0;j<workrecords.size();j++){
				Workrecord workrecord=workrecords.get(j);
				rpall+=workrecord.getHournums();

				if(workrecord.getRemarks()!=null){

					remarks.append(workrecord.getRemarks());
				}
				remarks.append("   ");
				remarks.append(workrecord.getHournums()>0?"+"+workrecord.getHournums():workrecord.getHournums());
				remarks.append("\r\n");
				
			}
			json.put("rpall", ""+rpall);
			json.put("remarks", remarks.toString());
			list.add(json);
		}
		return WriteJsonUtil.writeJSON(list);
	}

	@ManageAuthFilter
	@AjaxLoginFilter
	@RequestMapping(value="/gaiChuo")
	@ResponseBody
	public  String gaiChuo(HttpServletRequest request) throws IOException {
		System.out.println(request.getParameter("title"));
		Date thisstamp=new Date();

		HashMap<String , Object> out=new HashMap<String,Object>();
		
		Salaryindex salaryindex=new Salaryindex();
		salaryindex.setiTitle(request.getParameter("title"));
		salaryindex.setInsertTime(thisstamp);
		Assistant modifyman=(Assistant) request.getSession().getAttribute("assistant");
		salaryindex.setModifyMan(modifyman.getName());
		
		try {
			salaryindexService.gaiChuo(salaryindex);
			out.put("code", 0);
			return WriteJsonUtil.writeJSON(out);
		} catch(DataException e) {
			out.put("code", 1);
			out.put("errorstatus","sqlError");
			return WriteJsonUtil.writeJSON(out);
		}
	}
	
	
	
	
	
}
