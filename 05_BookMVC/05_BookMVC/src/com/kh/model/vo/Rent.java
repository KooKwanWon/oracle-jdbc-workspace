package com.kh.model.vo;

import java.util.Date;

public class Rent {
	private int rentNo;
	private Member rentMemNo;
	private Book rentBook;
	private Date rentDate;
	public Rent() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Rent(Member member, Book book) {
		super();
		this.rentMemNo = member;
		this.rentBook = book;
	}
	
	public Rent(int rentNo, Member rentMem, Book rentBookNo, Date rentDate) {
		super();
		this.rentNo = rentNo;
		this.rentMemNo = rentMem;
		this.rentBook = rentBookNo;
		this.rentDate = rentDate;
	}
	public int getRentNo() {
		return rentNo;
	}
	public void setRentNo(int rentNo) {
		this.rentNo = rentNo;
	}
	public Member getRentMemNo() {
		return rentMemNo;
	}
	public void setRentMem(Member rentMem) {
		this.rentMemNo = rentMem;
	}
	public Book getRentBookNo() {
		return rentBook;
	}
	public void setRentBookNo(Book rentBookNo) {
		this.rentBook = rentBookNo;
	}
	public Date getRentDate() {
		return rentDate;
	}
	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}
	
	
}
