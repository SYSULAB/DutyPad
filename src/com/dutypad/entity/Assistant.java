package com.dutypad.entity;


import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
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

import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "assistant", catalog = "dutypad")
public class Assistant implements java.io.Serializable{

	private static final long serialVersionUID=-1 ;
	
	private Integer id;
	private String sid;
	private String phone;
	private String cornet;
	private String password;
	private String name;
	private String bankcard;
	private String email;
	private Short power; 
	private Short onJob;
	@NumberFormat(pattern = "0.000") 
	private Double workhour;
	@NumberFormat(pattern = "0.000") 
	private Double savehour;
	
	@JsonIgnore
	private Set<Workrecord> workrecords= new HashSet<Workrecord>(0);
	@JsonIgnore
	private Set<Salaryrecord> salaryrecords= new HashSet<Salaryrecord>(0);
	
	
	public Assistant() {
	}
	
	
	


	public Assistant(Integer id, String sId, String phone, String cornet,
			String password, String name, String bankcard, String email,
			Short power, Short onJob, Double workhour, Double savehour,
			Set<Workrecord> workrecords, Set<Salaryrecord> salaryrecords) {
		
		this.id = id;
		this.sid = sId;
		this.phone = phone;
		this.cornet = cornet;
		this.password = password;
		this.name = name;
		this.bankcard = bankcard;
		this.email = email;
		this.power = power;
		this.onJob = onJob;
		this.workhour = workhour;
		this.savehour = savehour;
		this.workrecords = workrecords;
		this.salaryrecords = salaryrecords;
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

	
	@Column(name = "phone", length = 17)
	public String getPhone() {
		return phone;
	}
	
	

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	@Column(name = "s_id", length = 11)
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}

	
	@Column(name = "cornet", length = 9)
	public String getCornet() {
		return cornet;
	}
	public void setCornet(String cornet) {
		this.cornet = cornet;
	}
	
	@Column(name = "password", length = 35)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name = "name", length = 35)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "bankcard", length = 30)
	public String getBankcard() {
		return bankcard;
	}
	public void setBankcard(String bankcard) {
		this.bankcard = bankcard;
	}
	
	@Column(name = "email", length = 30)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "power")
	public Short getPower() {
		return power;
	}
	public void setPower(Short power) {
		this.power = power;
	}
	
	@Column(name = "on_job")
	public Short getOnJob() {
		return onJob;
	}
	public void setOnJob(Short onJob) {
		this.onJob = onJob;
	}
	
	@Column(name = "workhour")
	public Double getWorkhour() {
		return workhour;
	}
	public void setWorkhour(Double workhour) {
		this.workhour = workhour;
	}
	
	@Column(name = "savehour")
	public Double getSavehour() {
		return savehour;
	}
	public void setSavehour(Double savehour) {
		this.savehour = savehour;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "assistant")
	public Set<Workrecord> getWorkrecords() {
		return workrecords;
	}


	public void setWorkrecords(Set<Workrecord> workrecords) {
		this.workrecords = workrecords;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "assistant")
	public Set<Salaryrecord> getSalaryrecords() {
		return salaryrecords;
	}


	public void setSalaryrecords(Set<Salaryrecord> salaryrecords) {
		this.salaryrecords = salaryrecords;
	}
	
	
	
}
