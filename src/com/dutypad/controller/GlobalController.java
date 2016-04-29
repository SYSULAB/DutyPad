package com.dutypad.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import com.base.util.CustomMyDateEditor;
import com.base.util.CustomTimeEditor;


/**
 * 自定义日期、时间的类型绑定
 *	转换前端的string成为date,time格式
 */
@ControllerAdvice
public class GlobalController {
	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {
		//System.out.println("-------------------------------------------------------------");
		/*System.out.println(request.getParameter("recordDate").substring(4,15));*/
		//这关键的Locale.ENGLISH来解决了Mar问题，不然new Date(Mar 03 2013)是不行的，必须数字，但这个就解决了
		SimpleDateFormat myDateFormat = new SimpleDateFormat("MMM dd yyyy",Locale.ENGLISH);
		myDateFormat.setLenient(false);
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		timeFormat.setLenient(false);
	    
		
		binder.setAutoGrowCollectionLimit(500);
		//binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(java.util.Date.class, new CustomMyDateEditor(myDateFormat, true));
		binder.registerCustomEditor(java.sql.Time.class, new CustomTimeEditor(timeFormat, true));
	}
}
