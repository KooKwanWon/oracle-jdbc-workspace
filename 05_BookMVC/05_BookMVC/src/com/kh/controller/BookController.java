package com.kh.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import com.kh.model.dao.BookDAO;
import com.kh.model.vo.Book;
import com.kh.model.vo.Member;
import com.kh.model.vo.Rent;

public class BookController {
	private BookDAO dao = new BookDAO();
	private Member member = new Member();
	private Rent rent = new Rent();
	
	public ArrayList<Book> printBookAll() throws SQLException{
		//select
		
		
		return dao.printBookAll();
	}
	
	
	public boolean registerBook(Book book) throws SQLException {
		//insert
		
		if(dao.registerBook(book) > 0)
			return true;
		return false;
	}
	
	
	public boolean sellBook(int no) throws SQLException {
		//delete
		if(dao.sellBook(no) > 0)
			return true;
		return false;
	}
	
	
	public boolean registerMember(Member member) throws SQLException {
		if(dao.registerMember(member) > 0)
			return true;
		return false;
	}
	
	
	public Member login(String id, String password) throws SQLException {
		if(dao.login(id, password) != null) {
			member = dao.login(id, password);
			return member;
		}
		return null;
	}
	
	
	public boolean deleteMember() throws SQLException {
		
		if(dao.deleteMember(member.getMemId(), member.getMemPwd()) > 0) {
			return true;
		}
		return false;
	}
	
	
	public boolean rentBook(int no) {
		rent =new Rent(member, no);
		
		return false;
	}
	
	
	public boolean deleteRent(int no) {
	
		return false;
	}
	
	
	public ArrayList<Rent> printRentBook(){
		return null;
	}
}
