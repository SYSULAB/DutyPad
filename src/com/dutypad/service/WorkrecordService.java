package com.dutypad.service;

import com.dutypad.entity.Assistant;
import com.dutypad.entity.Workrecord;

public interface WorkrecordService extends BaseService<Workrecord>{
	void insertSigninRecord(Workrecord workrecord,Assistant assistant);
	void insertRPRecord(Workrecord workrecord,Assistant assistant);
}
