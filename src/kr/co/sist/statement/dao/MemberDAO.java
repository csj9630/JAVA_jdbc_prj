package kr.co.sist.statement.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.statement.dto.MemberDTO;
import kr.co.sist.statement.service.MemberService;

/**
 * DAO (Data Access Object): DBMS에 대한 작업만 정의 method명은 SQL문을 넣어서 정의함
 */
public class MemberDAO {

	/**
	 * 회원정보를 member table에 추가하는 일
	 * 
	 * @param mDTO 추가할 회원정보
	 */
	public void insertMember(MemberDTO mDTO) throws SQLException {
		// 1. 드라이버를 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // end catch
			// 2. 로딩된 드라이버를 사용하여 커넥션 얻기
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String id = "scott";
		String pass = "tiger";
		Connection con = null;
		Statement stmt = null;

		try {
			con = DriverManager.getConnection(url, id, pass);
			// 3. 쿼리문 생성 객체 얻기
			stmt = con.createStatement();

			// 홀따옴표 자리를 잘 볼 것.
//			String insertMember 
//			= "insert into member(num,name,age,gender,tel) values(seq_member.nextval,'"+mDTO.getName()+"',"+mDTO.getAge()+",'"+mDTO.getGender()+"','"+mDTO.getTel()+"')";

			
			//@formatter:off
			// 위에서 하던 걸 StringBuilder로 하기
			StringBuilder insertMember = new StringBuilder();
			insertMember.append("insert into member(num,name,age,gender,tel) values(seq_member.nextval,'")
					.append(mDTO.getName()).append("',")
					.append(mDTO.getAge()).append(",'")
					.append(mDTO.getGender()).append("','")
					.append(mDTO.getTel()).append("')");
			//@formatter:on
			
			
			
			// insertMember를 출력했을 때 SQL문이 제대로 나오는지 확인할 것.
			// 출력문을 Golden에서 돌려보기.
			System.out.println(insertMember);

			// 4. 쿼리문 수행 후 결과 얻기
			int cnt = stmt.executeUpdate(insertMember.toString());
			System.out.println(cnt + " 건이 추가되었습니다.");

		} finally {
			// 5. 연결 끊기
			if (stmt != null) {
				stmt.close();
			} // end if
			if (con != null) {
				con.close();
			} // end if

		} // end finally

	}// insertMember

	// insert, update와 delete는 비슷하게 돌아간다.

	/**
	 * 회원정보를 변경하는 일 insert 구조 그대로 가져와서 //회원번호를 사용하여 나이, 전화번호 변경
	 * 
	 * @param mDTO 회원정보
	 * @return 0-회원번호 없음, 1-회원번호로 삭제
	 * @throws SQLException DBMS에서 문제 발생.
	 */
	public int updateMember(MemberDTO mDTO) throws SQLException {
		int flag = 0;

		// 1. 드라이버를 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // end catch
			// 2. 로딩된 드라이버를 사용하여 커넥션 얻기
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String id = "scott";
		String pass = "tiger";
		Connection con = null;
		Statement stmt = null;

		try {
			con = DriverManager.getConnection(url, id, pass);
			// 3. 쿼리문 생성 객체 얻기
			stmt = con.createStatement();
			// 홀따옴표 자리를 잘 볼 것.
//					String insertMember 
//					= "insert into member(num,name,age,gender,tel) values(seq_member.nextval,'"+mDTO.getName()+"',"+mDTO.getAge()+",'"+mDTO.getGender()+"','"+mDTO.getTel()+"')";

			// 이걸 StringBuilder로 하기
			StringBuilder updateMember = new StringBuilder();

			//@formatter:off
			
			
			//회원번호를 사용하여 나이, 전화번호 변경
			updateMember
				.append("		update	member")
				.append("		set		age=").append(mDTO.getAge())
				.append("		,tel='").append(mDTO.getTel()).append("'")
				.append("		where	num=").append(mDTO.getNum());
			
		
			
			// insertMember를 출력했을 때 SQL문이 제대로 나오는지 확인할 것.
			// 출력문을 Golden에서 돌려보기.
//			System.out.println(updateMember);

			// 4. 쿼리문 수행 후 결과 얻기
			flag = stmt.executeUpdate(updateMember.toString());//변경한 행의 수가 리턴
			System.out.println(flag+"건 변경되었습니다.");

		} finally {
			// 5. 연결 끊기
			if (stmt != null) {stmt.close();} // end if
			if (con != null)  {con.close(); } // end if
		} // end finally
		
		//@formatter:on
		return flag;
	}// updateMember

	/**
	 * 회원정보를 삭제하는 일
	 * 
	 * @param memberNum 삭제할 회원번호
	 * @return 0-회원번호 없음, 1-회원번호로 삭제
	 * @throws SQLException DBMS에서 문제 발생.
	 */
	public int deleteMember(int memberNum) throws SQLException {
		int flag = 0;

		// 1. 드라이버를 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // end catch
			// 2. 로딩된 드라이버를 사용하여 커넥션 얻기
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String id = "scott";
		String pass = "tiger";
		Connection con = null;
		Statement stmt = null;

		try {
			con = DriverManager.getConnection(url, id, pass);
			// 3. 쿼리문 생성 객체 얻기
			stmt = con.createStatement();
		

			//@formatter:off
			
			//회원번호를 사용하여 레코드를 삭제한다.
			StringBuilder deleteMember = new StringBuilder();
			deleteMember
				.append("		delete from	member")
				.append("		where	num=").append(memberNum);
			
		
			
			// insertMember를 출력했을 때 SQL문이 제대로 나오는지 확인할 것.
			// 출력문을 Golden에서 돌려보기.
//			System.out.println(deleteMember);

			// 4. 쿼리문 수행 후 결과 얻기
			flag = stmt.executeUpdate(deleteMember.toString());//
			System.out.println(flag+"건 삭제되었습니다.");

		} finally {
			// 5. 연결 끊기
			if (stmt != null) {stmt.close();} // end if
			if (con != null)  {con.close(); } // end if
		} // end finally
		
		//@formatter:on
		return flag;
	}// deleteMember

	/**
	 * 모든 사원 정보를 검색 //1.드라이버 로딩 //2.커넥션 얻기 //3.쿼리문 생성객체 얻기 //4.쿼리문 수행 후 결과 얻기 (
	 * cursor의 제어권 얻기) //5.연결 끊기
	 * 
	 * @return 모든 사원 정보
	 * @throws SQLException
	 */
	public List<MemberDTO> selectAllMember() throws SQLException {
		List<MemberDTO> list = new ArrayList<MemberDTO>();

		// 1.드라이버 로딩

		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // end catch

		// 2.커넥션 얻기
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String id = "scott";
		String pass = "tiger";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url, id, pass);
			// 3. 쿼리문 생성 객체 얻기
			stmt = con.createStatement();

			// 4.쿼리문 수행 후 결과 얻기 ( cursor의 제어권 얻기)

			// order by 추가하여 순차 정렬
			String selectMember = "SELECT NUM, NAME, AGE, GENDER, TEL, INPUT_DATE FROM MEMBER order by num";

			// ResultSet : 조회결과를 움직일 수 있는 커서의 제어권을 받음.
			rs = stmt.executeQuery(selectMember);

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
			if (stmt != null) {
				stmt.close();
			} // end if
			if (con != null) {
				con.close();
			} // end if
			if (rs != null) {
				rs.close();
			} // end if

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
	public MemberDTO selectOneMember(int memberNum) throws SQLException {
		MemberDTO mDTO = null;

		// 1.드라이버 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // end catch

		// 2.커넥션 얻기
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String id = "scott";
		String pass = "tiger";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;

		// 이번엔 try with resources로 해보자.
		try {
			// 3.쿼리문 생성객체 얻기
			con = DriverManager.getConnection(url, id, pass);
			stmt = con.createStatement();

			// 4.쿼리문 수행 후 결과 얻기
			StringBuilder selectOneMember = new StringBuilder();
			selectOneMember.append("		SELECT NAME, AGE, GENDER, TEL, INPUT_DATE")
					.append("		FROM MEMBER								").append("		WHERE num=")
					.append(memberNum);

			rs = stmt.executeQuery(selectOneMember.toString());

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
			if (stmt != null) {
				stmt.close();
			} // end if
			if (con != null) {
				con.close();
			} // end if
			if (rs != null) {
				rs.close();
			} // end if

		} // end finally

		return mDTO;
	}// selectOneMember


}// class
