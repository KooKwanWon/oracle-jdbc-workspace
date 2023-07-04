package jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import config.ServerInfo;

public class MemberTest {

	

	public static void main(String[] args) {

		try {
			MemberTest mt = new MemberTest();
			Member m = new Member("user01", "1234", "user11");

			Class.forName(ServerInfo.DRIVER_NAME);
			System.out.println("Driver Loading....");

			mt.registerMember(m);
//			System.out.println("회원가입!");

			m.setPassword("123456");
			mt.updatePassword(m);
			System.out.println("비밀번호 변경!");

			m.setName("user1234");
			mt.updateName(m);
			System.out.println("이름 변경!");

			System.out.println(mt.getMember(m.getId()));

			System.out.println(mt.login(m));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

}
