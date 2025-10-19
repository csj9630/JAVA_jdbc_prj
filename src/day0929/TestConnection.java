package day0929;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import oracle.jdbc.driver.OracleDriver;

/**
 * 1. JDBC로 JVM과 DBMS를 연결한다.
 */
public class TestConnection {

	public TestConnection() {
		// 1. 드라이버 로딩

		try {
			//OracleDriver od = new OracleDriver();
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("드라이버 로딩 성공");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} // end catch

		// 2.Connection 얻기
//		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String url = "jdbc:oracle:thin:@192.168.10.82:1521:orcl";
		String id = "scott";
		String pass = "tiger";

		try {
			Connection con = DriverManager.getConnection(url, id, pass);
			System.out.println("20 4H connection : " + con);
			
			//Query문을 미리 세팅해서 DBMS에 보냄.
			PreparedStatement pstmt=con.prepareStatement("select empno from emp");
			ResultSet rs=pstmt.executeQuery();

			while( rs.next() ) {
			System.out.println("☆★☆★₯₯₯₤"+rs.getInt("empno")+"₯₯₯₤☆★☆★☆★");

			}//end while

		} catch (SQLException e) {
			e.printStackTrace();
		} // end catch

	}// TestConnection

	public static void main(String[] args) {
		new TestConnection();

	}// main

}// class
