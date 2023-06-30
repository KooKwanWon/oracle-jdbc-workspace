package jdbc;

import java.sql.*;
import java.util.*;
import java.util.Date;

import config.ServerInfo;

public class DBConnectionTest3 {

	public static void main(String[] args) {

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
			st.setString(1, "디자인팀");
			st.setInt(2, 1);

			int result = st.executeUpdate();

			System.out.println(result + "명 수정!");

			// . 쿼리문 실행
			String query1 = "SELECT * FROM EMP";
			st = conn.prepareStatement(query1);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				String empId = rs.getString("emp_id");
				String empName = rs.getString("emp_name");
				String deptTitle = rs.getString("dept_title");
				Date hireDate = rs.getDate("hire_date");
				
				System.out.print(empId + " / " + empName + " / " + deptTitle + " / " + hireDate);

			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
