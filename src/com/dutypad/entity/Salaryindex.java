package com.dutypad.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "salaryindex", catalog = "dutypad")
public class Salaryindex implements java.io.Serializable{
	private Integer id;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date insertTime;
	private String iTitle;
	private String modifyMan;
	
	
	@JsonIgnore
	private Set<Salaryrecord> salaryrecords= new HashSet<Salaryrecord>(0);
	
	public Salaryindex(){
		
	}
	
	
	
	public Salaryindex(Integer id, Date insertTime, String iTitle,String modifyMan,
			Set<Salaryrecord> salaryrecords) {
		this.id = id;
		this.insertTime = insertTime;
		this.iTitle = iTitle;
		this.salaryrecords = salaryrecords;
		this.modifyMan = modifyMan;
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
	
	@Column(name = "insert_time")
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	@Column(name = "i_title")
	public String getiTitle() {
		return iTitle;
	}
	public void setiTitle(String iTitle) {
		this.iTitle = iTitle;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "salaryindex")
	public Set<Salaryrecord> getSalaryrecords() {
		return salaryrecords;
	}

	public void setSalaryrecords(Set<Salaryrecord> salaryrecords) {
		this.salaryrecords = salaryrecords;
	}
	
	@Column(name = "modify_man" ,length=11)
	public String getModifyMan() {
		return modifyMan;
	}
	public void setModifyMan(String modifyMan) {
		this.modifyMan = modifyMan;
	}

	
}
