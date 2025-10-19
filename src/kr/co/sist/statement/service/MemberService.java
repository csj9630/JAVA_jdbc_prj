package kr.co.sist.statement.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import kr.co.sist.statement.dao.MemberDAO;
import kr.co.sist.statement.dto.MemberDTO;

/**
 * SQL문 처리하는 DAO 객체를 생성하여, 데이터 관리를 처리함. 현 시점에선 데이터 추가만 있음.
 */
public class MemberService {

	private MemberDAO mDAO;

	public MemberService() {
		mDAO = new MemberDAO();
	} // MemberService

	/**
	 * 데이터 추가.
	 * 
	 * @param mDTO
	 * @return
	 */
	public boolean addMember(MemberDTO mDTO) {
		boolean flag = false;// 기본은 실패 상태

		try {
			mDAO.insertMember(mDTO);
			flag = true; // 성공상태
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("입력값이 올바르지 않다.");
			e.printStackTrace();
		} // end catch

		return flag;
	}// addMember

	/**
	 * 모든 회원을 검색. DTO에 저장된 데이터를 문자열로 반환하여 그 모음을 list로 반환.
	 * 
	 * @return
	 */
	public List<String> searchAllMember() {
		List<String> list = new ArrayList<String>();
		StringBuilder searchMember = new StringBuilder();
		try {
			List<MemberDTO> tempList = mDAO.selectAllMember();
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy a", Locale.UK);

			for (MemberDTO mDTO : tempList) {
				searchMember.delete(0, searchMember.length());// 시작 전 데이터 초기화
				searchMember.append(mDTO.getNum()).append(",").append(mDTO.getName()).append(",").append(mDTO.getAge())
						.append(",").append(mDTO.getGender()).append(",").append(mDTO.getTel()).append(",")
						.append(sdf.format(mDTO.getInput_date()));

				list.add(searchMember.toString());
			} // end for
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // end catch

		return list;

	}// searchAllMember

	/**
	 * @param num
	 * @return
	 */
	public MemberDTO searchOneMember(int num) {
		MemberDTO mDTO = null;
		try {
			mDTO = mDAO.selectOneMember(num);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // end catch
		return mDTO;
	}// searchOneMember
	
	/**
	 * 회원정보를 변경 
	 * @param mDTO
	 * @return - 0 : 회원번호 없음, 1-회원번호 n있음, 2-DB의 문제
	 */
	public int modifyMember(MemberDTO mDTO) {
		int flag=0;
		try {
			flag = mDAO.updateMember(mDTO);
			//DB테이블에 회원번호가 pk가 아니라면 flag가 n개가 반환될 수 있고
			//그 경우 1로 재설정한다.
			
		} catch (SQLException e) {
			flag=2;
			e.printStackTrace();
		}// end catch
		
		return flag;
	}//modifyMember
	

	/**
	 * 회원번호를 입력 받고 그 회원을 삭제
	 * @param memberNum 삭제할 회원 번호
	 * @return 0 : 회원번호 없음, 1-회원번호 n있음, 2-DB의 문제
	 */
	public int removeMember(int memberNum) {
		int flag=0;
		try {
			flag = mDAO.deleteMember(memberNum);
			//DB테이블에 회원번호가 pk가 아니라면 flag가 n개가 반환될 수 있고
			//그 경우 1로 재설정한다.
			
		} catch (SQLException e) {
			flag=2;
			e.printStackTrace();
		}// end catch
		
		return flag;
	}//removeMember

}// class
