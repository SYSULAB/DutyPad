package com.dutypad.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.base.util.Constant;
import com.dutypad.dao.AssistantDao;
import com.dutypad.dao.LastwageDao;
import com.dutypad.dao.SalaryindexDao;
import com.dutypad.dao.SalaryrecordDao;
import com.dutypad.dao.WorkrecordDao;
import com.dutypad.entity.Assistant;
import com.dutypad.entity.Lastwage;
import com.dutypad.entity.Salaryindex;
import com.dutypad.entity.Salaryrecord;
import com.dutypad.entity.Workrecord;
import com.dutypad.service.SalaryindexService;

@Service
@Lazy(true)
public class SalaryindexServiceImpl extends BaseServiceImpl<Salaryindex> implements SalaryindexService{
	private SalaryindexDao salaryindexDao;
	private LastwageDao lastwageDao;
	private SalaryrecordDao salaryrecordDao;
	private AssistantDao assistantDao;
	private WorkrecordDao workrecordDao;
	
	
	
	
	public AssistantDao getAssistantDao() {
		return assistantDao;
	}
	
	@Resource
	public void setAssistantDao(AssistantDao assistantDao) {
		this.assistantDao = assistantDao;
	}

	public WorkrecordDao getWorkrecordDao() {
		return workrecordDao;
	}
	
	@Resource
	public void setWorkrecordDao(WorkrecordDao workrecordDao) {
		this.workrecordDao = workrecordDao;
	}

	public SalaryrecordDao getSalaryrecordDao() {
		return salaryrecordDao;
	}

	@Resource
	public void setSalaryrecordDao(SalaryrecordDao salaryrecordDao) {
		this.salaryrecordDao = salaryrecordDao;
	}

	public LastwageDao getLastwageDao() {
		return lastwageDao;
	}
	
	@Resource
	public void setLastwageDao(LastwageDao lastwageDao) {
		this.lastwageDao = lastwageDao;

	}

	public SalaryindexDao getSalaryindexDao() {
		return salaryindexDao;
	}

	@Resource
	public void setSalaryindexDao(SalaryindexDao salaryindexDao) {
		this.salaryindexDao = salaryindexDao;
		super.dao = salaryindexDao;
	}

	
	@Override
	public void gaiChuo(Salaryindex salaryindex) {
		salaryindexDao.persist(salaryindex);
		Date thisstamp=salaryindex.getInsertTime();
		Lastwage lastwage=lastwageDao.queryAll().get(0);
		Date laststamp=lastwage.getInsertTime();
		List<Assistant> assistants=assistantDao.queryByProperties("onJob", new Short((short)1));
		for(int i=0;i<assistants.size();i++){
			Assistant assistant=assistants.get(i);
			Double earnhour=assistant.getWorkhour()==null?0.00:assistant.getWorkhour();
			Double oldsavehour=assistant.getSavehour()==null?0.00:assistant.getSavehour();
			Salaryrecord salaryrecord=new Salaryrecord();
			salaryrecord.setEarnhour(earnhour);
			salaryrecord.setOldsave(oldsavehour);
			salaryrecord.setSalaryindex(salaryindex);
			salaryrecord.setAssistant(assistant);
			//这个40必须必须改成常量啊，甚至可以改成配置文件，然后写一个loadProperty
			double payhourd=earnhour+oldsavehour;
			payhourd=payhourd>Constant.PER_MONTH_PAY?Constant.PER_MONTH_PAY:payhourd;
			int payhouri=(int) payhourd;
			payhourd=(double)payhouri;
			double newsavehourd=earnhour+oldsavehour-payhourd;
			salaryrecord.setNewsave(newsavehourd);
			salaryrecord.setPayhour(payhourd);
			
			assistant.setWorkhour(0.000);
			assistant.setSavehour(newsavehourd);
			assistantDao.update(assistant);
			String[] propName=new String[]{"assistant","recordType"};
			Object[] propValue=new Object[]{assistant,new Short((short)2)};
			Map<String, String> sortedCondition=new HashMap<String, String>();
			sortedCondition.put("o.insertTime", "DESC");
			String betweenName="insertTime";
			List<Workrecord> workrecords=workrecordDao.queryByProperties(propName, propValue, sortedCondition,  betweenName, laststamp, thisstamp);
			StringBuffer salaryremark=new StringBuffer();
			double rpall=0;
			for(int j=0;j<workrecords.size();j++){
				Workrecord workrecord=workrecords.get(j);
				rpall+=workrecord.getHournums();

				if(workrecord.getRemarks()!=null){

					salaryremark.append(workrecord.getRemarks());
				}
				salaryremark.append("   ");
				salaryremark.append(workrecord.getHournums()>0?"+"+workrecord.getHournums():workrecord.getHournums());
				salaryremark.append("\r\n");
			}
			salaryrecord.setRemarks(salaryremark.toString());
			salaryrecord.setRpall(rpall);
			salaryrecordDao.persist(salaryrecord);
		}
		
		lastwage.setInsertTime(thisstamp);
		lastwageDao.update(lastwage);
	}
	
	
	
	
	
}
