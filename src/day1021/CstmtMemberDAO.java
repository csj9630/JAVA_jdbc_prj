package day1021;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.pstmt.dao.GetConnection;
import kr.co.sist.statement.dto.MemberDTO;
import oracle.jdbc.OracleTypes;

/**
 * DAO (Data Access Object): DBMS에 대한 작업만 정의 method명은 SQL문을 넣어서 정의함 메서드명은 sql문에
 * 넣어서 정의
 * 
 * 10-13-2025 : Singleton Pattern 적용
 */
public class CstmtMemberDAO {

	private static CstmtMemberDAO pmDAO;

	private CstmtMemberDAO() {
		super();
		System.out.println("cstmt 사용 중");
	}//

	/**
	 * 싱글톤 패턴으로 단일 객체만 생성.
	 * 
	 * @return
	 */
	public static CstmtMemberDAO getInstance() {
		if (pmDAO == null) {// 객체가 생성되어 있지 않을 때만
			pmDAO = new CstmtMemberDAO();// 새로 객체를 생성하라.

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
		CallableStatement cstmt = null;
		GetConnection gc = GetConnection.getInstance();

		try {
			con = gc.getConn();

			// 3. 쿼리문을 미리 설정하여 쿼리문 생성 객체를 얻는다.
			String insertMember = "insert into member(num,name,age,gender,tel) values(seq_member.nextval,?,?,?,?)";

			// PreparedStatement 세팅
			cstmt = con.prepareCall("{call insert_member(?,?,?,?,?,?)}");

			// 4. 바인드 변수 값을 설정함.
			// in parameter 등록
			cstmt.setString(1, mDTO.getName());
			cstmt.setInt(2, mDTO.getAge());
			cstmt.setString(3, mDTO.getGender());
			cstmt.setString(4, mDTO.getTel());

			// out parameter등록
			cstmt.registerOutParameter(5, Types.NUMERIC);
			cstmt.registerOutParameter(6, Types.VARCHAR);

			// 5.쿼리문 수행 후 결과 얻기
			cstmt.executeUpdate();

			// 6. 설정된 값 얻기
			rowCnt = cstmt.getInt(5);
			String msg = cstmt.getString(6);
			System.out.println(rowCnt + " / " + msg);
			// 알아서 닫히긴 하지만 혹시 모르니 수동도 추가.
			if (cstmt != null) {
				cstmt.close();
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			gc.dbClose(con, cstmt, null);
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
		CallableStatement cstmt = null;

		try {
			// 1.드라이버 로딩+2.커넥션 얻기
			con = gc.getConn();
			// 3. 쿼리문 생성 객체 얻기

			// 이걸 StringBuilder로 하기
			StringBuilder updateMember = new StringBuilder();

			//@formatter:off
					
			//회원번호를 사용하여 나이, 전화번호 변경
			cstmt=con.prepareCall( "{ call update_member(?,?,?, ?, ?) }" );
			//@formatter:on

			// 4. 바인드변수에 값 설정
			cstmt.setInt(1, mDTO.getAge());
			cstmt.setString(2, mDTO.getTel());
			cstmt.setInt(3, mDTO.getNum());

			// out parameter 등륵( oracle bind변수 사용 )
			cstmt.registerOutParameter(4, Types.NUMERIC);
			cstmt.registerOutParameter(5, Types.VARCHAR);

			// 5. 쿼리문 수행 후 결과 얻기
			cstmt.execute();// 변경한 행의 수가 리턴

			flag = cstmt.getInt(4);
			String msg = cstmt.getString(5);

			System.out.println(flag + "|| " + msg);

		} finally {
			// 5. 연결 끊기
			gc.dbClose(con, cstmt, null);
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
		CallableStatement cstmt = null;

		try {
			// 1.드라이버 로딩+2.커넥션 얻기
			con = gc.getConn();
			// 3. 쿼리문 생성 객체 얻기

			// 회원번호를 사용하여 레코드를 삭제한다.

			cstmt = con.prepareCall("{ call delete_member(?,?,?) }");

			// 4. 바인드변수에 값 설정
			cstmt.setInt(1, memberNum);

			// out parameter 등륵( oracle bind변수 사용 )
			cstmt.registerOutParameter(2, Types.NUMERIC);
			cstmt.registerOutParameter(3, Types.VARCHAR);

			// 4. 쿼리문 수행 후 결과 얻기
			cstmt.executeUpdate();// 변경한 행의 수

			flag = cstmt.getInt(2);
			String msg = cstmt.getString(3);
			System.out.println(flag + "|| " + msg);

		} finally {
			// 5. 연결 끊기
			gc.dbClose(con, cstmt, null);
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
		// PreparedStatement pstmt = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;

		System.out.println("CallableStatement로 Select All");
		GetConnection gc = GetConnection.getInstance();

		try {
			con = gc.getConn();
			// 3. 쿼리문 작성 + 쿼리문 생성 객체 얻기
			cstmt = con.prepareCall("{ call select_all_member(?)}");

			// 4.바인드 변수 값 설정
			cstmt.registerOutParameter(1, Types.REF_CURSOR);

			// 5.쿼리문 수행후 결과얻기 ( cursor의 제어권을 얻기 )
			// 조회결과를 움직일 수 있는 cursor의 제어권을 받음.
			cstmt.execute();
			// 6. out parameter의 값 받기
			rs = (ResultSet) cstmt.getObject(1);
			
			
			
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
			gc.dbClose(con, cstmt, rs);
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

			// 4. 바인드변수
			pstmt.setInt(1, memberNum);

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
