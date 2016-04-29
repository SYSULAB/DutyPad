package com.dutypad.service.impl;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.dutypad.dao.SalaryrecordDao;
import com.dutypad.entity.Salaryrecord;
import com.dutypad.service.SalaryrecordService;

@Service
@Lazy(true)
public class SalaryrecordServiceImpl extends BaseServiceImpl<Salaryrecord> implements SalaryrecordService{
	private SalaryrecordDao salaryrecordDao;

	public SalaryrecordDao getSalaryrecordDao() {
		return salaryrecordDao;
	}

	@Resource
	public void setSalaryrecordDao(SalaryrecordDao salaryrecordDao) {
		this.salaryrecordDao = salaryrecordDao;
		super.dao =salaryrecordDao;
	}
	
	
	
}
