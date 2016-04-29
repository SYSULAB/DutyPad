package com.dutypad.service.impl;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.dutypad.dao.LastwageDao;
import com.dutypad.entity.Lastwage;
import com.dutypad.service.LastwageService;
@Service
@Lazy(true)
public class LastwageServiceImpl extends BaseServiceImpl<Lastwage> implements LastwageService{
	private LastwageDao lastwageDao;

	public LastwageDao getLastwageDao() {
		return lastwageDao;
	}
	
	@Resource
	public void setLastwageDao(LastwageDao lastwageDao) {
		this.lastwageDao = lastwageDao;
		super.dao = lastwageDao;
	}
	
	
	
}
