package jdbc;

import java.sql.*;
import java.util.*;


public class DBConnectionTest2 {

	public static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	public static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	public static final String USER = "kh";
	public static final String PASSWORD = "kh";
	
	
	public static void main(String[] args) {
		
		try {
			// 드라이버 로딩
			Class.forName(DRIVER_NAME);
			System.out.println("Driver Loading...!");
						
			// 디비 연결
			Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("DB Connection...!!");
			
			// 3. Statement 객체 생성 -- INSERT
			String query = "INSERT INTO emp(emp_id, emp_name) VALUES(?, ?)";
			PreparedStatement st = conn.prepareStatement(query);
			
			// 4. 쿼리문 실행
			st.setInt(1, 1);
			st.setString(2, "구관원");
			
			int result = st.executeUpdate();
			
			System.out.println(result + "명 추가!");
			
			
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}

}
