package day1016;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import kr.co.sist.pstmt.dao.GetConnection;

public class TestTranaction {

	private Connection con =null;
	private GetConnection gc;
	
	public int insertTransaction(String name,String address) throws SQLException, IOException {
		int totalCnt =0;
		gc = GetConnection.getInstance();
		con = gc.getConn();
		
		//auto commit 해제해야함
		con.setAutoCommit(false);
		
		//1번째 Db 작업 수행
		String insertTranaction
		="insert into test_transaction(name, address)values(?,?)";
		PreparedStatement pstmt=con.prepareStatement(insertTranaction);
		pstmt.setString(1, name);
		pstmt.setString(2, address);
		int rowCnt=pstmt.executeUpdate();
		
		//2번째 Db 작업 수행
		String insertTranaction2
		="insert into test_transaction2(name, address)values(?,?)";
		PreparedStatement pstmt2=con.prepareStatement(insertTranaction2);
		pstmt2.setString(1, name);
		pstmt2.setString(2, address);
		int rowCnt2=pstmt2.executeUpdate();
		
		
		totalCnt = rowCnt+rowCnt2;
		
		
		return totalCnt;
	}//
	
	public void useInsertTransaction() {
		String name  = "민병조3";
		String addr = "1234567890123456";
		
		try {
			int totalcnt = insertTransaction(name, addr);
			
			if(totalcnt == 2) {//목표 행수라면
				con.commit(); // 트랜젝션을 정상 완료함.
				System.out.println("회원정보가 정상적으로 추가되었습니다.");
			}//end if
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				con.rollback();
				System.out.println("회원정보가 취소되었습니다.");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				gc.dbClose(con, null, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}//end fianlly
	}
	
	public static void main(String[] args) {
		TestTranaction tt = new TestTranaction();
		tt.useInsertTransaction();
	}//main
}//class
