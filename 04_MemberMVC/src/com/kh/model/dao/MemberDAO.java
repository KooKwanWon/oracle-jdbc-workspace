package com.kh.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.kh.model.vo.Member;

import config.ServerInfo;


public class MemberDAO implements MemberDAOTemplate {

	private Properties p = new Properties();

	public MemberDAO() {

		try {
			p.load(new FileInputStream("src/config/jdbc.properties"));
		} catch (IOException e) {

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

	public void registerMember(Member vo) throws SQLException {

		Connection conn = getConnect();
		PreparedStatement st = conn.prepareStatement(p.getProperty("addPerson"));

		conn.setAutoCommit(false);

		st.setString(1, vo.getId());
		st.setString(2, vo.getPassword());
		st.setString(3, vo.getName());

		
		PreparedStatement st2 = conn.prepareStatement(p.getProperty("searchAllPerson"));
		ResultSet rs = st2.executeQuery();
		int result = st.executeUpdate();
		
		while (rs.next()) {

			if (rs.getString("id").equals(vo.getId())) {
				conn.rollback();
				System.out.println("Id : " + vo.getId() + "가 중복되었습니다.");
				System.out.println("Member 등록 실패");
			} else if(result==1){
				conn.commit();
				System.out.println(vo.getName() + "님, 추가!");
			}
		}

		closeAll(st, conn);
	}

	public void updatePassword(Member vo) throws SQLException {

		Connection conn = getConnect();
		PreparedStatement st = conn.prepareStatement(p.getProperty("updatePerson"));

		st.setString(2, vo.getId());
		st.setString(1, vo.getPassword());

		int result = st.executeUpdate();
		System.out.println(result + "명 수정");

		closeAll(st, conn);

	}

	public void updateName(Member vo) throws SQLException {

		Connection conn = getConnect();
		PreparedStatement st = conn.prepareStatement(p.getProperty("updateName"));

		st.setString(2, vo.getId());
		st.setString(1, vo.getName());

		int result = st.executeUpdate();
		System.out.println(result + "명 수정");

		closeAll(st, conn);
	}

	public Member getMember(String id) throws SQLException {

		Connection conn = getConnect();
		PreparedStatement st = conn.prepareStatement(p.getProperty("viewPerson"));

		st.setString(1, id);
		ResultSet rs = st.executeQuery();

		if(rs.next()) {
			Member a = new Member(rs.getString("id"), rs.getString("password"), rs.getString("name"));
			return a;
		}
		
		closeAll(rs, st, conn);

		return null;
	}
	

	public Member login(Member vo) throws SQLException {

		Connection conn = getConnect();
		PreparedStatement st = conn.prepareStatement(p.getProperty("searchAllPerson"));

		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			Member a = new Member(rs.getString("id"), rs.getString("password"), rs.getString("name"));

			if (vo.getId().equals(a.getId()) & vo.getPassword().equals(a.getPassword())
					& vo.getName().equals(a.getName())) {
				System.out.println(vo.getName() + "님 로그인에 성공하였습니다.");
			}
		}
		closeAll(rs, st, conn);

		return vo;
	}
}
