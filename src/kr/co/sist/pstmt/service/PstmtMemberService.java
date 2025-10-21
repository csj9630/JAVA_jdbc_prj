package kr.co.sist.pstmt.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import day1021.CstmtMemberDAO;
import kr.co.sist.pstmt.dao.PstmtMemberDAO;
import kr.co.sist.statement.dto.MemberDTO;

/**
 * SQL문 처리하는 DAO 객체를 생성하여, 데이터 관리를 처리함.<br>
 *  10-13-2025 : Singleton Pattern 적용으로 생성자 삭제
 */
public class PstmtMemberService {

	public PstmtMemberService() {
	} // MemberService

	/**
	 * 데이터 추가.
	 * 
	 * @param mDTO
	 * @return
	 */
	public boolean addMember(MemberDTO mDTO) {
		boolean flag = false;// 기본은 실패 상태

		//PstmtMemberDAO pmDAO = PstmtMemberDAO.getInstance();
		CstmtMemberDAO pmDAO = CstmtMemberDAO.getInstance();
	

		try {
			flag = pmDAO.insertMember(mDTO) == 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // DB에 추가작업이 문제 없다면

		return flag;
	}// addMember

	/**
	 * 모든 회원을 검색. DTO에 저장된 데이터를 문자열로 반환하여 그 모음을 list로 반환.
	 * 
	 * @return
	 */
	public List<MemberDTO> searchAllMember() {
		List<MemberDTO> list = null;

		try {
			PstmtMemberDAO pmDAO = PstmtMemberDAO.getInstance();
			list = pmDAO.selectAllMember();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}// searchAllMember

	/**
	 * @param num
	 * @return
	 */
	public MemberDTO searchOneMember(int num) {
		MemberDTO mDTO = null;
		try {

			PstmtMemberDAO pmDAO = PstmtMemberDAO.getInstance();

			mDTO = pmDAO.selectOneMember(num);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // end catch
		return mDTO;
	}// searchOneMember

	/**
	 * 회원정보를 변경
	 * 
	 * @param mDTO
	 * @return - 0 : 회원번호 없음, 1-회원번호 n있음, 2-DB의 문제
	 */
	public int modifyMember(MemberDTO mDTO) {
		int flag = 0;
		try {
//			PstmtMemberDAO pmDAO = PstmtMemberDAO.getInstance();
			CstmtMemberDAO pmDAO = CstmtMemberDAO.getInstance();
			try {
				flag = pmDAO.updateMember(mDTO);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// DB테이블에 회원번호가 pk가 아니라면 flag가 n개가 반환될 수 있고
			// 그 경우 1로 재설정한다.

		} catch (SQLException e) {
			flag = 2;
			e.printStackTrace();
		} // end catch

		return flag;
	}// modifyMember

	/**
	 * 회원번호를 입력 받고 그 회원을 삭제
	 * 
	 * @param memberNum 삭제할 회원 번호
	 * @return 0 : 회원번호 없음, 1-회원번호 n있음, 2-DB의 문제
	 */
	public int removeMember(int memberNum) {
		int flag = 0;
		try {
//			PstmtMemberDAO pmDAO = PstmtMemberDAO.getInstance();
			CstmtMemberDAO pmDAO = CstmtMemberDAO.getInstance();

			try {
				flag = pmDAO.deleteMember(memberNum);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// DB테이블에 회원번호가 pk가 아니라면 flag가 n개가 반환될 수 있고
			// 그 경우 1로 재설정한다.

		} catch (SQLException e) {
			flag = 2;
			e.printStackTrace();
		} // end catch

		return flag;
	}// removeMember

}// class
