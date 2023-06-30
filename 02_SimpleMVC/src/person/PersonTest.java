package person;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import config.ServerInfo;

public class PersonTest {

	private Properties p = new Properties();

	public static void main(String[] args) {

		PersonTest pt = new PersonTest();

		try {
			Class.forName(ServerInfo.DRIVER_NAME);
			System.out.println("Driver Loading....");

			pt.addPerson("김강우", "서울");
			pt.addPerson("고아라", "제주도");
			pt.addPerson("강태주", "경기도");

			pt.searchAllPerson();

			pt.removePerson(3); // 강태주 삭제

			pt.updatePerson(1, "제주도");

			pt.viewPerson(1);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public PersonTest() {
		try {
			p.load(new FileInputStream("src/config/jdbc.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 고정적인 반복 -- 디비연결, 자원 반납
	public Connection getConnect() throws SQLException {
		Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASSWORD);
		return conn;
	}

	public void closeAll(Connection conn, PreparedStatement st) throws SQLException {
		if (st != null)
			st.close();
		if (conn != null)
			conn.close();
	}

	public void closeAll(Connection conn, PreparedStatement st, ResultSet rs) throws SQLException {
		if (rs != null)
			rs.close();
		closeAll(conn, st);
	}

	public void addPerson(String name, String address) {

		try {
			Properties p = new Properties();
			p.load(new FileInputStream("src/config/jdbc.properties"));

			// 1. 드라이버 로딩
			Class.forName(ServerInfo.DRIVER_NAME);
			System.out.println("Driver Loading....!!");

			// 2. 디비 연결
			Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASSWORD);
			System.out.println("DB Connection...!!");

			// 3. Statement 객체 생성
			String query = p.getProperty("jdbc.sql.insert");
			PreparedStatement st = conn.prepareStatement(query);

			// 4. 쿼리문 실행
			st.setString(1, name);
			st.setString(2, address);

			System.out.println(name + "님 추가!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void removePerson(int id) {

		try {
			Properties p = new Properties();
			p.load(new FileInputStream("src/config/jdbc.properties"));

			// 1. 드라이버 로딩
			Class.forName(ServerInfo.DRIVER_NAME);
			System.out.println("Driver Loading....!!");

			// 2. 디비 연결
			Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASSWORD);
			System.out.println("DB Connection...!!");

			// 3. Statement 객체 생성 - DELETE
			String query = p.getProperty("jdbc.sql.delete");
			PreparedStatement st = conn.prepareStatement(query);

			// 4. 쿼리문 실행

			st.setInt(1, id);

			int result = st.executeUpdate();
			System.out.println(result + "명 삭제!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void updatePerson(int id, String address) {

		try {
			// 드라이버 로딩
			Class.forName(ServerInfo.DRIVER_NAME);
			System.out.println("Driver Loading...!");

			// 디비 연결
			Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASSWORD);
			System.out.println("DB Connection...!!");

			// 3. Statement 객체 생성 -- UPDATE
			String query = "UPDATE emp SET dept_title = ? WHERE emp_id = ?";
			PreparedStatement st = conn.prepareStatement(query);

			// 4. 쿼리문 실행
			st.setString(1, address);
			st.setInt(2, id);

			int result = st.executeUpdate();

			System.out.println(result + "명 수정!");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void searchAllPerson() {

		try {
			// 드라이버 로딩
			Class.forName(ServerInfo.DRIVER_NAME);
			System.out.println("Driver Loading...!");

			// 디비 연결
			Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASSWORD);
			System.out.println("DB Connection...!!");

			// . 쿼리문 실행
			String query = "SELECT * FROM PERSON";
			PreparedStatement st = conn.prepareStatement(query);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String address = rs.getString("address");

				System.out.print(id + " / " + name + " / " + address + "\n");

			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void viewPerson(int id) {

		try {
			Properties p = new Properties();
			p.load(new FileInputStream("src/config/jdbc.properties"));

			// 1. 드라이버 로딩
			Class.forName(ServerInfo.DRIVER_NAME);
			System.out.println("Driver Loading....!!");

			// 2. 디비 연결
			Connection conn = DriverManager.getConnection(ServerInfo.URL, ServerInfo.USER, ServerInfo.PASSWORD);
			System.out.println("DB Connection...!!");

			// 3. Statement 객체 생성 - DELETE
			String query = p.getProperty("jdbc.sql.select");
			PreparedStatement st = conn.prepareStatement(query);

			// 4. 쿼리문 실행

			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				String id1 = rs.getString("id");
				String name = rs.getString("name");
				String address = rs.getString("address");

				System.out.print(id1 + " / " + name + " / " + address + "\n");

			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
