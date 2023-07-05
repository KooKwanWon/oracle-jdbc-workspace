package com.kh.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.kh.model.dao.BookDAO;
import com.kh.model.vo.Book;
import com.kh.model.vo.Member;
import com.kh.model.vo.Rent;

public class BookController {
	private BookDAO dao = new BookDAO();
	private Member member = new Member();
	
	
	public ArrayList<Book> printBookAll() throws SQLException{
		
		return dao.printBookAll();
	}
	
	
	public boolean registerBook(Book book) throws SQLException {
		//insert
		if(dao.registerBook(book) > 0) {
			
			return true;
		}else
		return false;
	}
	
	
	public boolean sellBook(int no) throws SQLException {
		
		if(dao.sellBook(no) > 0) {
			
			return true;
		}else
			
		return false;
	}
	
	
	public boolean registerMember(Member member) throws SQLException {
		
		if(dao.registerMember(member) > 0) {
			
			return true;
		}else
		return false;
	}
	
	
	public Member login(String id, String password) throws SQLException {
		
		member = dao.login(id, password);
	    return member;
	}
	
	
	public boolean deleteMember() throws SQLException {
		
		if(dao.deleteMember(member.getMemId(), member.getMemPwd()) > 0) {
			
			return true;
		}else
		return false;
	}
	
	
	public boolean rentBook(int no) throws SQLException {
					
		Rent rent = new Rent(member, dao.searchBook(no));
		
		
		if(dao.rentBook(rent) > 0) {
			return true;
		}else
		return false;
	}
	
	
	public boolean deleteRent(int no) {
	
		return false;
	}
	
	
	public ArrayList<Rent> printRentBook(){
		Rent rent = new Rent();
		
//		rent.setBook(new Book(rs.getString("BK_TITLE"), rs.getString("BK_AUTHOR")));
		
		return null;
	}
}
