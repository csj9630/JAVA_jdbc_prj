package day1016;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.pstmt.dao.GetConnection;

public class ZipcodeDAO {
	private static ZipcodeDAO zDAO;

	private ZipcodeDAO(){
		
	}

	public static ZipcodeDAO getInstance() {
		if(zDAO == null) {
		zDAO=new ZipcodeDAO();
		}//end if
		return zDAO;
	}// getInstance

	public List<ZipcodeDTO> useStmtSelectZipcode(String dong) throws SQLException, IOException{
		List<ZipcodeDTO> list=new ArrayList<ZipcodeDTO>();
		//1. 드라이버로딩
		//2. 커넥션얻기
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		GetConnection gc = GetConnection.getInstance();
		
		try {
			con = gc.getConn();
			//3. 쿼리문 생성객체 얻기 
			//4. 쿼리문 수행후 결과얻기

			stmt=con.createStatement();
	
			StringBuilder selectZipcode=new StringBuilder();

			selectZipcode
			.append("	select zipcode, sido, gugun, dong, bunji	")
			.append("  from ZIPCODE     ")
			.append("   where dong like '").append(dong).append("%'  ");
//			.append("   where dong like '").append(injectionStrBlock(dong)).append("%'  ");

			
			System.out.println(selectZipcode);
			rs=stmt.executeQuery(selectZipcode.toString());
			
			ZipcodeDTO zDTO =null;
			while(rs.next()) {
				zDTO = new ZipcodeDTO(rs.getString("zipcode"), rs.getString("sido"), rs.getString("gugun"), rs.getString("dong"), rs.getString("bunji"));
				list.add(zDTO);
			}//end while


		} finally {
			// 5.연결 끊기
			gc.dbClose(con, stmt, rs);
		} // end finally
		
//		System.out.println(list);
		return list;
	}//useStmtSelectZipcode
	
	/**
	 * 쿼리문에 관련된 문자열을 전부 치환한다.
	 * @param dong
	 * @return
	 */
	public String injectionStrBlock (String dong) {
		String str =dong;
		
		
		if(!str.isEmpty()) {
			str = str.replaceAll(" ","").replaceAll("union", "").replaceAll("select","");
		}
		return str;
	}
	
	
	public List<ZipcodeDTO> usePstmtSelectZipcode(String dong) throws SQLException, IOException{
			List<ZipcodeDTO> list=new ArrayList<ZipcodeDTO>();
			
			//1. 드라이버로딩
			//2. 커넥션얻기
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			GetConnection gc = GetConnection.getInstance();
			
			try {
				con = gc.getConn();
				//3. 쿼리문 생성객체 얻기 
				

		
				StringBuilder selectZipcode=new StringBuilder();

				selectZipcode
				.append("	select zipcode, sido, gugun, dong, bunji	")
				.append("  from ZIPCODE     ")
				.append("   where dong like ?||'%'  ");//%를 일반문자로, 문자열 붙임연산자(||)로 처리
//				.append("   where dong like '?%'  ");//바인드 변수는 % 문자열과 합쳐졌을 때 인식을 못하는 문제가 있다.

				pstmt=con.prepareStatement(selectZipcode.toString());
		
				//4.바인드 변수에 값 설정
				pstmt.setString(1, dong);
				
				//4. 쿼리문 수행후 결과얻기
				rs=pstmt.executeQuery();
				
				
				ZipcodeDTO zDTO =null;
				while(rs.next()) {
					zDTO = new ZipcodeDTO(rs.getString("zipcode"), rs.getString("sido"), rs.getString("gugun"), rs.getString("dong"), rs.getString("bunji"));
					list.add(zDTO);
				}//end while


			} finally {
				// 5.연결 끊기
				gc.dbClose(con, pstmt, rs);
			} // end finally
			
			return list;
	}//usePstmtSelectZipcode
	



}//class
