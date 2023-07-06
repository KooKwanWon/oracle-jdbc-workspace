package com.kh.model.vo;

import java.util.Date;

public class Member {
	private int memNo;
	private String memId;
	private String memPwd;
	private String memName;
	private char status;
	private Date enrollDate;
	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Member(String id, String password, String name) {
		this.memId = id;
		this.memPwd = password;
		this.memName = name;
	}
	public Member(int memNo, String memId, String memPwd, String memName, char status, Date enrollDate) {
		super();
		this.memNo = memNo;
		this.memId = memId;
		this.memPwd = memPwd;
		this.memName = memName;
		this.status = status;
		this.enrollDate = enrollDate;
	}
	public Member(int memNo) {
		this.memNo = memNo;
	}

	public int getMemNo() {
		return memNo;
	}
	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemPwd() {
		return memPwd;
	}
	public void setMemPwd(String memPwd) {
		this.memPwd = memPwd;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	public Date getEnrollDate() {
		return enrollDate;
	}
	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}
	
	
	
}
