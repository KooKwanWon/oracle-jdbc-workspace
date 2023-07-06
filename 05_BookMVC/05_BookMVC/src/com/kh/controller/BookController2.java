package com.kh.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import com.kh.model.dao.BookDAO2;
import com.kh.model.vo.Book;
import com.kh.model.vo.Member;
import com.kh.model.vo.Rent;

public class BookController2 {

	private BookDAO2 dao = new BookDAO2();
	private Member member = new Member(); // 로그인 정보 여기에 담아요!
	
	public ArrayList<Book> printBookAll() {
		
		try {
			return dao.printBookAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean registerBook(Book book) {
		
		try {
			if(dao.registerBook(book) == 1) return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean sellBook(int no) {
		
		try {
			if(dao.sellBook(no) == 1) return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean registerMember(Member member) {
		
		try {
			if(dao.registerMember(member) == 1) return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public Member login(String id, String password) {
		
		try {
			member = dao.login(id, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return member;
	}
	
	public boolean deleteMember() {
		// 위에 member 변수있잖아요~~~
		// 로그인때 담아놓아서~~ 매개변수 따로 안 받은 겁니다!
		try {
			if(dao.deleteMember(member.getMemId(), member.getMemPwd()) == 1)return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean rentBook(int no) {
		
		try {
			if(dao.rentBook(new Rent(new Member(member.getMemNo()), new Book(no))) == 1) return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteRent(int no) {
		try {
			if(dao.deleteRent(no) == 1) return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<Rent> printRentBook() {
		
		
		try {
			return dao.printRentBook(member.getMemId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}