package kr.co.sist.pstmt.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import day1013.Singleton;
import kr.co.sist.statement.dto.MemberDTO;
import kr.co.sist.statement.service.MemberService;

/**
 * DAO (Data Access Object): DBMS에 대한 작업만 정의 method명은 SQL문을 넣어서 정의함 메서드명은 sql문에
 * 넣어서 정의
 * 
 * 10-13-2025 : Singleton Pattern 적용
 */
public class PstmtMemberDAO {

	private static PstmtMemberDAO pmDAO;

	private PstmtMemberDAO() {
		super();
	}//

	/**
	 * 싱글톤 패턴으로 단일 객체만 생성.
	 * 
	 * @return
	 */
	public static PstmtMemberDAO getInstance() {
		if (pmDAO == null) {// 객체가 생성되어 있지 않을 때만
			pmDAO = new PstmtMemberDAO();// 새로 객체를 생성하라.

		} // end if
		return pmDAO;
	}// getInstance

	/**
	 * 회원정보를 member table에 추가하는 일
	 * 
	 * @param mDTO 추가할 회원정보
	 * @return 추가된 행의 수 1-성공, 0-실패
	 * @throws Exception
	 */
	public int insertMember(MemberDTO mDTO) throws SQLException, IOException {

		int rowCnt = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		GetConnection gc = GetConnection.getInstance();

		try {
			con = gc.getConn();

			// 3. 쿼리문을 미리 설정하여 쿼리문 생성 객체를 얻는다.
			String insertMember = "insert into member(num,name,age,gender,tel) values(seq_member.nextval,?,?,?,?)";

			// PreparedStatement 세팅
			pstmt = con.prepareStatement(insertMember);

			// 4. 바인드 변수 값을 설정함.
			pstmt.setString(1, mDTO.getName());
			pstmt.setInt(2, mDTO.getAge());
			pstmt.setString(3, mDTO.getGender());
			pstmt.setString(4, mDTO.getTel());

			// 5.쿼리문 수행 후 결과 얻기

			rowCnt = pstmt.executeUpdate();

			// 알아서 닫히긴 하지만 혹시 모르니 수동도 추가.
			if (pstmt != null) {
				pstmt.close();
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			gc.dbClose(con, pstmt, null);
		} // end finally
		return rowCnt;
	}// insertMember

	// insert, update와 delete는 비슷하게 돌아간다.

	/**
	 * 회원정보를 변경하는 일 insert 구조 그대로 가져와서 //회원번호를 사용하여 나이, 전화번호 변경
	 * 
	 * @param mDTO 회원정보
	 * @return 0-회원번호 없음, 1-회원번호로 삭제
	 * @throws SQLException DBMS에서 문제 발생.
	 * @throws IOException
	 */
	public int updateMember(MemberDTO mDTO) throws SQLException, IOException {
		int flag = 0;

		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 1.드라이버 로딩+2.커넥션 얻기
			con = gc.getConn();
			// 3. 쿼리문 생성 객체 얻기

			// 이걸 StringBuilder로 하기
			StringBuilder updateMember = new StringBuilder();

			//@formatter:off
					
			//회원번호를 사용하여 나이, 전화번호 변경
			updateMember
				.append("		update	member")
				.append("		set		age=?,tel=?")
				.append("		where	num=?");
			//@formatter:on

			pstmt = con.prepareStatement(updateMember.toString());

			// 4. 바인드변수에 값 설정
			pstmt.setInt(1, mDTO.getAge());
			pstmt.setString(2, mDTO.getTel());
			pstmt.setInt(3, mDTO.getNum());

			// 5. 쿼리문 수행 후 결과 얻기
			flag = pstmt.executeUpdate();// 변경한 행의 수가 리턴

		} finally {
			// 5. 연결 끊기
			gc.dbClose(con, pstmt, null);
		} // end finally

		return flag;
	}// updateMember

	/**
	 * 회원정보를 삭제하는 일
	 * 
	 * @param memberNum 삭제할 회원번호
	 * @return 0-회원번호 없음, 1-회원번호로 삭제
	 * @throws SQLException DBMS에서 문제 발생.
	 */
	public int deleteMember(int memberNum) throws SQLException, IOException {
		int flag = 0;
		GetConnection gc = GetConnection.getInstance();

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			// 1.드라이버 로딩+2.커넥션 얻기
			con = gc.getConn();
			// 3. 쿼리문 생성 객체 얻기

			// 회원번호를 사용하여 레코드를 삭제한다.
			String deleteMember = "delete from	member where num=?";

			pstmt = con.prepareStatement(deleteMember);

			// 4. 바인드변수에 값 설정
			pstmt.setInt(1, memberNum);

			// 4. 쿼리문 수행 후 결과 얻기
			flag = pstmt.executeUpdate();//변경한 행의 수

		} finally {
			// 5. 연결 끊기
			gc.dbClose(con, pstmt, null);
		} // end finally

		return flag;
	}// deleteMember

	/**
	 * 모든 사원 정보를 검색 //1.드라이버 로딩 //2.커넥션 얻기 //3.쿼리문 생성객체 얻기 //4.쿼리문 수행 후 결과 얻기 (
	 * cursor의 제어권 얻기) //5.연결 끊기
	 * 
	 * @return 모든 사원 정보
	 * @throws SQLException
	 * @throws IOException 
	 */
	public List<MemberDTO> selectAllMember() throws SQLException, IOException {
		List<MemberDTO> list = new ArrayList<MemberDTO>();

		// 1.드라이버 로딩	
		// 2.커넥션 얻기

	
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		GetConnection gc = GetConnection.getInstance();

		try {
			con = gc.getConn();
			// 3.  쿼리문 작성 + 쿼리문 생성 객체 얻기


			// order by ~ dese 추가하여 내림차순 정렬
			String selectMember = "SELECT NUM, NAME, AGE, GENDER, TEL, INPUT_DATE FROM MEMBER order by num desc";
			pstmt = con.prepareStatement(selectMember);

					// 4.쿼리문 수행 후 결과 얻기 ( cursor의 제어권 얻기)
			// ResultSet : 조회결과를 움직일 수 있는 커서의 제어권을 받음.
			rs = pstmt.executeQuery(selectMember);

			int num = 0;// 회원번호
			String name = "";// 회원명
			int age = 0;// 회원 나이
			String gender = "";// 회원성별
			String tel = "";// 회원 전화번호
			Date inputDate = null;

			MemberDTO mDTO = null;

			while (rs.next()) { // 조회결과의 다음 레코드가 존재하는 동안 반복.
				num = rs.getInt("num");
				name = rs.getString("name");
				age = rs.getInt("age");
				gender = rs.getString("gender");
				tel = rs.getString("tel");
				inputDate = rs.getDate("INPUT_DATE");// simpleDateFormat 등으로 형식 바꿀 수 있다.

//				System.out.println(num+"/"+name+"/"+age+"/"+gender+"/"+tel+"/"+inputDate);

				// 조회 결과를 DTO에 저장하여 하나로 묶어서 관리한다.
				mDTO = new MemberDTO(num, name, age, gender, tel, inputDate);

				// 레코드의 컬럼 한줄을 저장하기 위해 DTO를 사용함.
				// DTO : DBMS 커서가 있는 위치에 레코드 값을 DTO 객체 하나에 저장한다.

				// 같은 이름의 객체가 사라지지 않게 list에 추가한다.
				list.add(mDTO);

			} // end while

			// list에 데이터가 들어왔는지 체크.
//			System.out.println(list.size());
//			System.out.println(list.isEmpty());;

		} finally {
			// 5.연결 끊기
			gc.dbClose(con, pstmt, rs);
		} // end finally

		return list;
	}// selectA11Member

	/**
	 * select해서 레코드 한 줄만 찾는 메서드.
	 * 
	 * @param memberNum
	 * @return
	 * @throws SQLException
	 */
	public MemberDTO selectOneMember(int memberNum) throws SQLException, IOException {
		MemberDTO mDTO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		GetConnection gc = GetConnection.getInstance();
		
		try {
			con = gc.getConn();


			// 4.쿼리문 수행 후 결과 얻기
			
			StringBuilder selectOneMember = new StringBuilder();
			//@formatter:off
			selectOneMember
			.append("		SELECT NAME, AGE, GENDER, TEL, INPUT_DATE")
			.append("		FROM MEMBER								")
			.append("		WHERE num=?");
			//@formatter:on
			
			// 3.쿼리문 생성객체 얻기
			pstmt = con.prepareStatement(selectOneMember.toString());

			//4. 바인드변수
			pstmt.setInt(1,memberNum);
			
			rs = pstmt.executeQuery();

			if (rs.next()) {// 쿼리로 인한 조회 결과가 존재함.
				mDTO = new MemberDTO();
				mDTO.setNum(memberNum);
				mDTO.setName(rs.getString("name"));
				mDTO.setAge(rs.getInt("Age"));
				mDTO.setGender(rs.getString("Gender"));
				mDTO.setTel(rs.getString("TEL"));
				mDTO.setInput_date(rs.getDate("Input_date"));

			} // end if

		} finally {
			// 5.연결 끊기
			gc.dbClose(con, pstmt, rs);

		} // end finally

		return mDTO;
	}// selectOneMember

}// class
