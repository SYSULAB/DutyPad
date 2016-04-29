package com.dutypad.service.impl;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.dutypad.dao.AssistantDao;
import com.dutypad.entity.Assistant;
import com.dutypad.service.AssistantService;

@Service
@Lazy(true)
public class AssistantServiceImpl extends BaseServiceImpl<Assistant> implements AssistantService{
	private AssistantDao assistantDao;
	
	
	public AssistantDao getAssistantDao() {
		return assistantDao;
	}


	@Resource
	public void setAssistantDao(AssistantDao assistantDao) {
		this.assistantDao = assistantDao;
		super.dao = assistantDao;
	}
}
