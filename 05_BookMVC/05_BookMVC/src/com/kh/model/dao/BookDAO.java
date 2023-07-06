package com.kh.model.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.model.vo.Book;
import com.kh.model.vo.Member;
import com.kh.model.vo.Rent;

import config.ServerInfo;

public class BookDAO implements BookDAOTemplate{

	private Properties p = new Properties();
	
	
	private ArrayList<Book> book = new ArrayList<>(); 
	
	public BookDAO() {
		try {
			p.load(new FileInputStream("src/config/jdbc.properties"));
			Class.forName(ServerInfo.DRIVER_NAME);
			System.out.println("Driver Loading...");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public Connection getConnect() throws SQLException {
		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASSWORD);
		
		return conn;
	}

	@Override
	public void closeAll(PreparedStatement st, Connection conn) throws SQLException {
		if(st != null) {
			st.close();
			conn.close();
		}
		
	}

	@Override
	public void closeAll(ResultSet rs, PreparedStatement st, Connection conn) throws SQLException {
		if(rs != null) {
			rs.close();
			st.close();
			conn.close();
		}
	}

	@Override
	public ArrayList<Book> printBookAll() throws SQLException {
		
		book.clear();
		
		Connection conn = getConnect();
		
		PreparedStatement st = conn.prepareStatement(p.getProperty("printBookAll"));
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next()) {
			book.add(new Book(rs.getInt("BK_NO"), rs.getString("BK_TITLE"), rs.getString("BK_AUTHOR")));
		}
		
		closeAll(rs, st, conn);
		return book;
	}

	@Override
	public int registerBook(Book book) throws SQLException {
		Connection conn = getConnect();
		
		PreparedStatement st = conn.prepareStatement(p.getProperty("registerBook"));
		PreparedStatement st2 = conn.prepareStatement(p.getProperty("printBook"));
		conn.setAutoCommit(false);
		st2.setString(1, book.getBkTitle());
		st2.setString(2, book.getBkAuthor());
		ResultSet rs = st2.executeQuery();
		
		
		if(rs.next()) {
			System.out.println("이미 등록된 책입니다.");
			conn.rollback();
			return 0;
		}
		
		st.setString(1, book.getBkTitle());
		st.setString(2, book.getBkAuthor());
		int result = st.executeUpdate();
		conn.commit();
		closeAll(rs, st, conn);
		return result;
		
		
		
			
	}

	@Override
	public int sellBook(int no) throws SQLException {
		Connection conn = getConnect();
		
		PreparedStatement st = conn.prepareStatement(p.getProperty("sellBook"));
		conn.setAutoCommit(false);
		
		
		st.setInt(1, no);
		
		int result = st.executeUpdate();
		
		if(result < 1) {
			System.out.println("존재하지 않는 책입니다.");
			conn.rollback();
			closeAll(st, conn);
			return result;
		}
		
		conn.commit();
		closeAll(st, conn);
		
		return result;
	}

	@Override
	public int registerMember(Member member) throws SQLException {
		Connection conn = getConnect();
		
		PreparedStatement st = conn.prepareStatement(p.getProperty("registerMem"));
		PreparedStatement st2 = conn.prepareStatement(p.getProperty("loginMem"));
		conn.setAutoCommit(false);
		st2.setString(1, member.getMemId());
		st2.setString(2, member.getMemPwd());
		ResultSet rs = st2.executeQuery();
		
		
		if(rs.next()) {
			System.out.println("중복된 아이디 입니다.");
			conn.rollback();
			return 0;
		}
		
		st.setString(1, member.getMemId());
		st.setString(2, member.getMemPwd());
		st.setString(3, member.getMemName());
		int result = st.executeUpdate();
		conn.commit();
		closeAll(rs, st, conn);
		return result;
	}

	@Override
	public Member login(String id, String password) throws SQLException {
		Connection conn = getConnect();
		
		
		PreparedStatement st2 = conn.prepareStatement(p.getProperty("loginMem"));
		st2.setString(1, id);
		st2.setString(2, password);
		ResultSet rs = st2.executeQuery();
		
		
		if(rs.next()) {
			Member success = new Member(rs.getString("MEMBER_ID"), rs.getString("MEMBER_PWD"), rs.getString("MEMBER_NAME"));
			return success;
		}
		return null;
	}

	@Override
	public int deleteMember(String id, String password) throws SQLException {
		Connection conn = getConnect();
		
		PreparedStatement st = conn.prepareStatement(p.getProperty("deleteMem"));
		PreparedStatement st2 = conn.prepareStatement(p.getProperty("loginMem"));
		conn.setAutoCommit(false);
		st2.setString(1, id);
		st2.setString(2, password);
		ResultSet rs = st2.executeQuery();
		
		
		if(rs.next()) {
			st.setInt(1, rs.getInt("MEMBER_NO"));
			int result = st.executeUpdate();
			
			conn.commit();
			closeAll(rs, st, conn);
			return result;
		}
		return 0;
	}

	@Override
	public int rentBook(Rent rent) throws SQLException {
		
		
		// 책 대여 기능!! INSERT ~~ TB_RENT
		return 0;
	}

	@Override
	public int deleteRent(int no) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Rent> printRentBook(String id) throws SQLException {
		// join 필요
		return null;
	}

}
