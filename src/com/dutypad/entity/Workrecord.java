package com.dutypad.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "workrecord", catalog = "dutypad")
public class Workrecord implements java.io.Serializable{
	private Integer id;
	private Short recordType;
	

	@DateTimeFormat(pattern="yyyy-MM")
	private Date recordDate;
	@DateTimeFormat(pattern="HH:mm")
	private Time startTime;
	@DateTimeFormat(pattern="HH:mm")
	private Time endTime;
	private String classroom;
	private Boolean extraWork;
	private String remarks;
	private String todosth;
	private Short todoState;
	@DateTimeFormat(pattern="yyyy-MM-dd hh:mm:ss")
	private Date insertTime;
	private Double hournums;
	private Short rewardPunish;
	private String modifyMan;
	private Assistant assistant;
	
	public Workrecord(){
		
	}

	
	
	public Workrecord(Integer id, Date recordDate, Time startTime,
			Time endTime, String classroom, Boolean extraWork, String remarks,
			String todosth, Short todoState, Date insertTime,
			Double hournums, Short rewardPunish, String modifyMan,
			Assistant assistant,Short recordType) {
		this.id = id;
		this.recordDate = recordDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.classroom = classroom;
		this.extraWork = extraWork;
		this.remarks = remarks;
		this.todosth = todosth;
		this.todoState = todoState;
		this.insertTime = insertTime;
		this.hournums = hournums;
		this.rewardPunish = rewardPunish;
		this.modifyMan = modifyMan;
		this.assistant = assistant;
		this.recordType=recordType;
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
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "record_date")
	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	@Column(name = "start_time")
	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time")
	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	
	@Column(name = "classroom",length=6)
	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	@Column(name = "extra_work")
	public Boolean getExtraWork() {
		return extraWork;
	}

	public void setExtraWork(Boolean extraWork) {
		this.extraWork = extraWork;
	}

	@Column(name = "remarks")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Column(name = "todosth")
	public String getTodosth() {
		return todosth;
	}

	
	public void setTodosth(String todosth) {
		this.todosth = todosth;
	}
	
	
	@Column(name = "todo_state")
	public Short getTodoState() {
		return todoState;
	}

	public void setTodoState(Short todoState) {
		this.todoState = todoState;
	}

	
	@Column(name = "insert_time")
	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	@Column(name = "hour_nums")
	public Double getHournums() {
		return hournums;
	}

	public void setHournums(Double hournums) {
		this.hournums = hournums;
	}

	
	@Column(name = "reward_punish")
	public Short getRewardPunish() {
		return rewardPunish;
	}

	public void setRewardPunish(Short rewardPunish) {
		this.rewardPunish = rewardPunish;
	}

	@Column(name = "modify_man" ,length=11)
	public String getModifyMan() {
		return modifyMan;
	}

	public void setModifyMan(String modifyMan) {
		this.modifyMan = modifyMan;
	}

	
	@JsonProperty(value = "assistname")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
	@JsonIdentityReference(alwaysAsId = true) 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "a_id")
	public Assistant getAssistant() {
		return assistant;
	}

	public void setAssistant(Assistant assistant) {
		this.assistant = assistant;
	}
	
	@Column(name = "record_type")
	public Short getRecordType() {
		return recordType;
	}



	public void setRecordType(Short recordType) {
		this.recordType = recordType;
	}
	
}
