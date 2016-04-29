package com.dutypad.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.dutypad.dao.AssistantDao;
import com.dutypad.dao.WorkrecordDao;
import com.dutypad.entity.Assistant;
import com.dutypad.entity.Workrecord;
import com.dutypad.service.WorkrecordService;

@Service
@Lazy(true)
public class WorkrecordServiceImpl extends BaseServiceImpl<Workrecord> implements WorkrecordService{
	private WorkrecordDao workrecordDao;
	private AssistantDao assistantDao;

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
		super.dao = workrecordDao;
	}

	@Override
	public void insertSigninRecord(Workrecord workrecord, Assistant assistant) {
		workrecordDao.persist(workrecord);
		
		assistantDao.update(assistant);
		
	}

	@Override
	public void insertRPRecord(Workrecord workrecord, Assistant assistant) {

		workrecordDao.persist(workrecord);
		assistantDao.update(assistant);

	}
	
	
}
