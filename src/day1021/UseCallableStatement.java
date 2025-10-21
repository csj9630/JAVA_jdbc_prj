package day1021;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Iterator;

import kr.co.sist.pstmt.dao.GetConnection;

public class UseCallableStatement {

	public void useCallabe(int num, int num2) throws SQLException, IOException, InterruptedException {
		// 1. 드라이버 로딩
		// 2. 커넥션 얻기

		Connection con = null;
		CallableStatement cstmt = null;

		GetConnection gc = GetConnection.getInstance();
		try {
			con = gc.getConn();
			// 3. 쿼리문 실행객체 얻기
			cstmt = con.prepareCall("{call plus(?,?,?,?) }");

			// 4.바인드 변수에 값 설정
			// in parameter 등록
			cstmt.setInt(1, num);
			cstmt.setInt(2, num2);
			// out parameter등록
			// out parameter 등륵( oracle bind변수 사용 )
			cstmt.registerOutParameter(3, Types.NUMERIC);
			cstmt.registerOutParameter(4, Types.VARCHAR);

			// 5. 쿼리문 수행
			cstmt.execute();

			// 6.out parameter에 저장된 값 얻기
			int sum = cstmt.getInt(3);
			String msg = cstmt.getString(4);

			System.out.println("결과 값 " + sum);
			System.out.println("|| " + msg);

		} finally {
			// 7.연결 끊기
			gc.dbClose(con, cstmt, null);
		} // finally emd

	}// useCallabe

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		UseCallableStatement ucs = new UseCallableStatement();

		try {
			for (int i = 0; i < 100; i++) {
				ucs.useCallabe(10, 22);
				Thread.sleep(100);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}// main

}// class
