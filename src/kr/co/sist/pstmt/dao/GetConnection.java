package kr.co.sist.pstmt.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class GetConnection {
	private static GetConnection getCon;

	private GetConnection() {
		super();
	}// GetConnection

	public static GetConnection getInstance() {
		if (getCon == null) {// 객체가 생성되어 있지 않을 때만
			getCon = new GetConnection();// 새로 객체를 생성하라.

		} // end if
		return getCon;
	}// getInstance

	public static Connection getConn() throws SQLException, IOException {
		
		// 1. 드라이버를 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // end catch
		
		
		// 2. 로딩된 드라이버를 사용하여 커넥션 얻기 => Properties 도입.
		//Properties 파일 경로를 가져와서 File 연결.
		File file = new File("C:\\dev\\workspace\\JAVA_jdbc_prj\\src\\properties\\database.properties");
		if(!file.exists()) {//파일이 존재하지 않는지 체크
			throw new IOException("Properties가 지정된 위치가 없습니다.");
		}//end if
		
		//Properties 객체 생성하여 FileStream 연결 후 값 가져오기.
		Properties prop = new Properties();
		prop.load(new FileInputStream(file));
		
		String url = prop.getProperty("url");
		String id = prop.getProperty("id");
		String pass = prop.getProperty("pass");


		//Connection 객체 생성 후 Return
		Connection con = DriverManager.getConnection(url, id, pass);
		
		return con;
	}// getCon
	
	/**
	 * Connection 닫는 Method.
	 * @param con
	 * @param stmt
	 * @param rs
	 * @throws SQLException 
	 */
	public void dbClose(Connection con, Statement stmt, ResultSet rs) throws SQLException {
		try {
			if(rs != null) {rs.close();}
			if(stmt != null) {stmt.close();}	
		}finally { //다른 게 다 예외 나와도 커넥션 만큼은 반드시 끊는다.
			if(con != null) {con.close();}
		}//end finally
	}//dbclose
}// class
