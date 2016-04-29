package com.dutypad.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "lastwage", catalog = "dutypad")
public class Lastwage implements java.io.Serializable{
	private Integer id;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date insertTime;
	
	public Lastwage(){
		
	}
	
	
	
	public Lastwage(Integer id, Date insertTime) {
		this.id = id;
		this.insertTime = insertTime;
	}



	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "laststamp")
	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	
}
