package com.kh.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.model.vo.Book;
import com.kh.model.vo.Member;
import com.kh.model.vo.Rent;

import config.ServerInfo;


public class BookDAO implements BookDAOTemplate {

	private Properties p = new Properties();

	private ArrayList<Book> book = new ArrayList<Book>();
	
	
	public BookDAO() {

		try {
			p.load(new FileInputStream("src/config/jdbc.properties"));
			Class.forName(ServerInfo.DRIVER_NAME);
		
		} catch (IOException e) {

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Connection getConnect() throws SQLException {

		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASSWORD);

		return conn;
	}

	public void closeAll(PreparedStatement st, Connection conn) throws SQLException {
		if (st != null)
			st.close();
		if (conn != null)
			conn.close();
	}

	public void closeAll(ResultSet rs, PreparedStatement st, Connection conn) throws SQLException {
		if (rs != null)
			rs.close();
		closeAll(st, conn);
	}

	@Override
	public ArrayList<Book> printBookAll() throws SQLException {

		book.clear();
		
		Connection con = getConnect();
		PreparedStatement st = con.prepareStatement(p.getProperty("printBookAll"));
		ResultSet rs = st.executeQuery();
		
		while(rs.next()) {
		book.add(new Book(rs.getInt("BK_NO"), rs.getString("BK_TITLE"), rs.getString("BK_AUTHOR")));
			
		}
			
		return book;
	
	}

	@Override
	public int registerBook(Book book) throws SQLException {

		Connection conn = getConnect();
		PreparedStatement st = conn.prepareStatement(p.getProperty("registerBook"));

		conn.setAutoCommit(false);

		st.setString(1, book.getBkTitle());
		st.setString(2, book.getBkAuthor());

		
		PreparedStatement st2 = conn.prepareStatement(p.getProperty("printBookAll"));
		ResultSet rs = st2.executeQuery();
		int result = st.executeUpdate();
		
		while (rs.next()) {

			if (rs.getString("BK_TITLE").equals(book.getBkTitle())) {
				conn.rollback();
				System.out.println("등록 실패");
			} else if(result==1){
				conn.commit();
				System.out.println(book.getBkTitle() + " 추가!");
			}
		}

		closeAll(st, conn);
		return result;
	}

	@Override
	public int sellBook(int no) throws SQLException {
		
		Connection conn = getConnect();
		PreparedStatement st = conn.prepareStatement(p.getProperty("sellBook"));

		st.setInt(1, no);
		
		int result = st.executeUpdate();

		closeAll(st, conn);
		
		return result;
	}

	@Override
	public int registerMember(Member member) throws SQLException {

		Connection conn = getConnect();
		PreparedStatement st = conn.prepareStatement(p.getProperty("registerMem"));

		conn.setAutoCommit(false);

		st.setString(1, member.getMemId());
		st.setString(2, member.getMemPwd());
		st.setString(3, member.getMemName());

		
		PreparedStatement st2 = conn.prepareStatement(p.getProperty("searchAllPerson"));
		ResultSet rs = st2.executeQuery();
		int result = st.executeUpdate();
		
		while (rs.next()) {

			if (rs.getString("MEMBER_ID").equals(member.getMemId())) {
				conn.rollback();
				System.out.println("Id : " + member.getMemId() + "가 중복되었습니다.");
				System.out.println("Member 등록 실패");
			} else if(result==1){
				conn.commit();
				System.out.println(member.getMemName() + "님, 추가!");
			}
		}

		closeAll(st, conn);
		return result;
	}

	@Override
	public Member login(String id, String password) throws SQLException {

		Connection conn = getConnect();
		
		PreparedStatement st = conn.prepareStatement(p.getProperty("searchAllPerson"));

		ResultSet rs = st.executeQuery();

		while (rs.next()) {
						
			if (id.equals(rs.getString("MEMBER_ID")) & password.equals(rs.getString("MEMBER_PWD"))) {
				Member a = new Member(rs.getInt("MEMBER_NO") , rs.getString("MEMBER_ID"), rs.getString("MEMBER_PWD"), rs.getString("MEMBER_NAME")
						, rs.getString("STATUS").charAt(0), rs.getDate("ENROLL_DATE"));
				return a;
			}
		}
		closeAll(rs, st, conn);
		
		return null;
	}

	@Override
	public int deleteMember(String id, String password) throws SQLException {
	
		Connection conn = getConnect();
		PreparedStatement st = conn.prepareStatement(p.getProperty("deleteMem"));

		st.setString(1, id);
		st.setString(2, password);
		
		int result = st.executeUpdate();

		closeAll(st, conn);
		
		return result;
	}

	@Override
	public int rentBook(Rent rent) throws SQLException {
		
		Connection conn = getConnect();
		PreparedStatement st = conn.prepareStatement(p.getProperty("rentBook"));

		st.setInt(1, (rent.getMember()).getMemNo());
		st.setInt(2, (rent.getBook()).getBkNo());
		
		int result = st.executeUpdate();

		closeAll(st, conn);
		
		return result;
		
	}

	@Override
	public int deleteRent(int no) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Rent> printRentBook(String id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Book searchBook(int no) throws SQLException {
		
		Connection conn = getConnect();
		
		PreparedStatement st = conn.prepareStatement(p.getProperty("searchBook"));

		st.setInt(1, no);
		
		ResultSet rs = st.executeQuery();

		rs.next();
					
		Book a = new Book(rs.getInt("BK_NO"), rs.getString("BK_TITLE"), rs.getString("BK_AUTHOR"));
				
		closeAll(rs, st, conn);
		
		return a;
	}
}
	

