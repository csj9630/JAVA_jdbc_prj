package testPackage;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import day1013.Singleton;
import kr.co.sist.pstmt.dao.GetConnection;
import kr.co.sist.pstmt.dao.PstmtMemberDAO;
import kr.co.sist.pstmt.service.PstmtMemberService;
import kr.co.sist.statement.dto.MemberDTO;

class PstmtTest {

//	@Disabled
//	@DisplayName ("DAO-회원정보 추가")
//	@Test
//	void testDAO() {
//		PstmtMemberDAO pmDAO = new PstmtMemberDAO();
//		MemberDTO mDTO = new MemberDTO(0, "민병조", 12, "남자", "010-58594-545", null);
//		assertEquals(pmDAO.insertMember(mDTO), 1);//1이 성공.
//	}
	@Disabled
	@DisplayName("Service회원정보 추가테스트")
	@Test
	void testService() {
		PstmtMemberService pms = new PstmtMemberService();
		MemberDTO mDTO = new MemberDTO(0, "볼트론", 5, "사자", "010-3662-3845", null);
		assertTrue(pms.addMember(mDTO));
	}

	@Disabled
	@DisplayName("Singleton Pattron 테스트")
	@Test
	void testSingleton() {
		Singleton s = Singleton.getInstance();
		Singleton s2 = Singleton.getInstance();

		// 객체의 주소가 같은가?
		assertSame(s, s2);
		// 객체의 주소가 다른가?
//		assertNotSame(s, s2);
	}

	@Disabled
	@DisplayName("DB connection 테스트")
	@Test
	void getConnection() {
		GetConnection gc = GetConnection.getInstance();
		try {
			Connection con = gc.getConn();
			assertNotNull(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Disabled
	@DisplayName("DAO 회원정보 추가 테스트")
	@Test
	void testDAO() {
		PstmtMemberDAO pmDAO = PstmtMemberDAO.getInstance();
		MemberDTO mDTO = new MemberDTO(0, "볼트론", 5, "사자", "010-3662-3845", null);
		try {
			assertEquals(pmDAO.insertMember(mDTO), 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Disabled
	@DisplayName("DAO 회원정보 변경 테스트")
	@Test
	void testUpdateMember() {
		PstmtMemberDAO pmDAO = PstmtMemberDAO.getInstance();
		MemberDTO mDTO = new MemberDTO(22, null, 999, null, "010-1111-1111", null);
		try {
			assertEquals(pmDAO.updateMember(mDTO), 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Disabled
	@DisplayName("DAO 회원정보 삭제 테스트")
	@Test
	void test_DeleteMember() {
		PstmtMemberDAO pmDAO = PstmtMemberDAO.getInstance();
		try {
			assertEquals(pmDAO.deleteMember(1), 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//test

	@Disabled
	@DisplayName("DAO 회원정보 전체 조회 테스트")
	@Test
	void test_SelectAllMember() {
	
		PstmtMemberService pms = new PstmtMemberService();

		//select 조회수가 member테이블의 레코드수가 일치하는가?
		assertEquals(pms.searchAllMember().size(), 2);
	}//test

	
	@DisplayName("DAO 회원정보 번호 조회 테스트")
	@Test
	void test_SelectOneMember() {
		
		PstmtMemberService pms = new PstmtMemberService();
		
		//1번 회원 조회 시 null이 아닌가?
		assertNotNull(pms.searchOneMember(1));
	}//test

}// class
