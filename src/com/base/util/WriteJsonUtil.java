package com.base.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
/*
 * 2015/12/11 21:00
 */
public class WriteJsonUtil {

	public WriteJsonUtil() {
	}
	


	public static void writeJSON(HttpServletResponse response, String json) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
	}

	public static void writeJSON(HttpServletResponse response, Object obj) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		JsonGenerator responseJsonGenerator = new ObjectMapper().getFactory().createGenerator(response.getOutputStream(), JsonEncoding.UTF8);
		responseJsonGenerator.writeObject(obj);
	}
	
	public static String writeJSON(Object obj, String [] ignoreNames) throws IOException {
		ObjectMapper mapper = new ObjectMapper();  
		mapper.addMixIn(Object.class, PropertyFilterMixIn.class);  
		
		FilterProvider filters = new SimpleFilterProvider().
				addFilter("IgnorePropertyByName", SimpleBeanPropertyFilter.serializeAllExcept(ignoreNames));  
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mapper.setDateFormat(df);
		
		ObjectWriter writer = mapper.writer(filters);  
		return writer.writeValueAsString(obj);
	}
	
	public static String writeJSON(Object obj) throws IOException {
		ObjectMapper mapper = new ObjectMapper();  
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mapper.setDateFormat(df);
		return mapper.writer().writeValueAsString(obj);
	}

	@JsonFilter("IgnorePropertyByName")  
	class PropertyFilterMixIn {}  
}
