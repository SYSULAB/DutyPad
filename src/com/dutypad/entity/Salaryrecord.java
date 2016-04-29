package com.dutypad.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "salaryrecord", catalog = "dutypad")
public class Salaryrecord implements java.io.Serializable{
	private Integer id;
	@NumberFormat(pattern = "0.000") 
	private Double oldsave;
	@NumberFormat(pattern = "0.000") 
	private Double newsave;
	@NumberFormat(pattern = "0.000") 
	private Double earnhour;
	@NumberFormat(pattern = "0.000") 
	private Double payhour;
	@NumberFormat(pattern = "0.000") 
	private Double rpall;
	
	private String remarks;
	@JsonIgnore
	private Assistant assistant;
	@JsonIgnore
	private Salaryindex salaryindex;
	
	public Salaryrecord(){
		
	}
	
	

	public Salaryrecord(Integer id, Double oldsave, Double newsave,
			Double earnhour, Double payhour, String remarks,
			 Assistant assistant, Salaryindex salaryindex,Double rpall) {
		this.id = id;
		this.oldsave = oldsave;
		this.newsave = newsave;
		this.earnhour = earnhour;
		this.payhour = payhour;
		this.remarks = remarks;
		this.assistant = assistant;
		this.salaryindex = salaryindex;
		this.rpall=rpall;
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
	
	@Column(name = "old_save")
	public Double getOldsave() {
		return oldsave;
	}
	public void setOldsave(Double oldsave) {
		this.oldsave = oldsave;
	}
	
	@Column(name = "new_save")
	public Double getNewsave() {
		return newsave;
	}
	public void setNewsave(Double newsave) {
		this.newsave = newsave;
	}
	
	@Column(name = "earn_hour")
	public Double getEarnhour() {
		return earnhour;
	}
	public void setEarnhour(Double earnhour) {
		this.earnhour = earnhour;
	}
	
	@Column(name = "pay_hour")
	public Double getPayhour() {
		return payhour;
	}
	public void setPayhour(Double payhour) {
		this.payhour = payhour;
	}
	
	@Column(name = "remarks")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "a_id")
	public Assistant getAssistant() {
		return assistant;
	}


	public void setAssistant(Assistant assistant) {
		this.assistant = assistant;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "i_id")
	public Salaryindex getSalaryindex() {
		return salaryindex;
	}


	public void setSalaryindex(Salaryindex salaryindex) {
		this.salaryindex = salaryindex;
	}
	
	@Column(name = "rp_all")
	public Double getRpall() {
		return rpall;
	}



	public void setRpall(Double rpall) {
		this.rpall = rpall;
	}
	
	

}
