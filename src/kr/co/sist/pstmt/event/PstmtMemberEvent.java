package kr.co.sist.pstmt.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import kr.co.sist.pstmt.design.PstmtMemberDesign;
import kr.co.sist.pstmt.design.PstmtMemberLeftPanel;
import kr.co.sist.pstmt.design.PstmtMemberRightPanel;
import kr.co.sist.pstmt.service.PstmtMemberService;
import kr.co.sist.statement.dto.MemberDTO;

/**
 * 액션 리스너와 취할 액션을 만든 클래스<br>
 * 액션 리스너 -> 편의용 메서드 -> DB 동작할 메서드 순으로 배치됨 <br>
 */
public class PstmtMemberEvent extends WindowAdapter
		implements ActionListener, /* ListSelectionListener, */MouseListener {

	private PstmtMemberDesign md;// MemberDesign 클래스와 HasA 관계
	private PstmtMemberService ms; //

	public PstmtMemberEvent(PstmtMemberDesign md) {
		// hasA 관계랑
		this.md = md;
		ms = new PstmtMemberService(); // MemberService 객체를 생성.
	}// MemberEvent

	// ---------------------------액션리스너------------------------------------------------
	/**
	 * 버튼 액션 리스너 Getter로 SouthPanel을 가져오고 거기서 버튼을 가져온다.
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == md.getMsp().getJbtnAdd()) {// 추가
			try {
				addMember();
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(md, "나이는 정수로만 입력해주세뇨");
			} // end catch
		} // end if

		if (ae.getSource() == md.getMsp().getJbtnModify()) {// 변경
			try {
				modifyMember();
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(md, "나이는 정수로만 입력해주세뇨오");
			} // end catch
		} // end if

		if (ae.getSource() == md.getMsp().getJbtnRemove()) {// 삭제
			try {
				removeMember();
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(md, "나이는 정수로만 입력해주세뇨오오");
			} // end catch
		} // end if

		if (ae.getSource() == md.getMsp().getJbtnClose()) {// 닫기
			md.dispose();
		} // end if

	}// actionPerformed

	/**
	 * 윈도우 닫기 버튼
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		md.dispose();
	}

	/**
	 * 휠 포함한 모든 클릭이 반응한다.
	 * 
	 * @param me
	 */
	@Override
	public void mouseClicked(MouseEvent me) {

		switch (me.getButton()) {
		case MouseEvent.BUTTON1:// 좌클릭
			try {
				searchOneMember();// DB연동하는 방식.

			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(md, "회원정보가 없습니다.");
			} // end catch

		}// end switch
	}// mouseClicked

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

//---------------------------편의용 메서드------------------------------------------------
	/**
	 * 모든 버튼 동작 후, 입력 칸을 비워준다.
	 */
	private void resetInputField() {
		PstmtMemberLeftPanel mlp = md.getMlp();
		// 입력칸을 초기화

		mlp.getJtfNum().setText("");
		mlp.getJtfName().setText("");
		mlp.getJtfAge().setText("");
		mlp.getJtfGender().setText("");
		mlp.getJtfTel().setText("");
		mlp.getJtfInputDate().setText("");

		mlp.getJtfName().requestFocus();
	}// resetInputField

//---------------------------DB 동작 메서드------------------------------------------------	
	/**
	 * 모든 회원을 검색하여 dlm에 설정한다.
	 */
	public void searchAllmember() {
		// 컴포넌트 객체를 얻는다.
		// 오른쪽 패널을 얻는다.
		PstmtMemberRightPanel prp = md.getMrp();

		// DefaultTable 모델을 얻는다.
		DefaultTableModel dtmMember = prp.getDtmMember();

		// DB에서 모든 회원정보를 검색함.
		PstmtMemberService pms = new PstmtMemberService();
		List<MemberDTO> listMember = pms.searchAllMember();

		// 조회된 회원정보를 JTable에 Row로 추가한다.
		String[] rowData = null;

		// 날짜 포맷
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 날짜포맷

		// JTable 초기화
		dtmMember.setRowCount(0);

		// 모든 레코드를 반복함.
		for (MemberDTO mDTO : listMember) {
			rowData = new String[6]; // JTable 컬럼수로 생성

			// 레코드 하나의 값을 배열에 저장.
			rowData[0] = String.valueOf(mDTO.getNum());
			rowData[1] = String.valueOf(mDTO.getAge());
			rowData[2] = (mDTO.getName());
			rowData[3] = (mDTO.getGender());
			rowData[4] = (mDTO.getTel());
			rowData[5] = sdf.format(mDTO.getInput_date());

			// 배열을 J테이블에 추가한다.
			dtmMember.addRow(rowData);
		} // end for

	}// searchAllmember

	/**
	 * select 하나만
	 * Jtable에서 선택한 행의 컬럼 값으로 DB table에 한 행을 조회한다.
	 */
	public void searchOneMember() {

		// JTable에 선택된 행의 컬럼값을 얻어서, DB table에 한 행 조회 작업
		PstmtMemberRightPanel pmrp = md.getMrp();
		DefaultTableModel dtm = pmrp.getDtmMember();
		JTable jt = pmrp.getJtMember();
		
		
	
		int selectedRow = jt.getSelectedRow();
		int selectedColumn = jt.getSelectedColumn();
		System.out.println(jt.getSelectedRow()+","+jt.getSelectedColumn()); 	//선택한 레코드의 row, column 번호가 나오는 메서드
		System.out.println(dtm.getValueAt(selectedRow, selectedColumn)); 		//선택한 레코드의 값을 출력
		System.out.println(dtm.getValueAt(selectedRow, 0)); 		//선택한 레코드의 회원번호(0번째 컬럼)만 출력)
		
		//선택한 레코드의 회원번호를 저장
		int memberNum = Integer.parseInt((String) dtm.getValueAt(selectedRow, 0)); //object 형을 string로 Casting 후 parseInt
		
		//회원번호로 1행 select 하기
		PstmtMemberService pms = new PstmtMemberService();
		MemberDTO mDTO = pms.searchOneMember(memberNum);
		
		
		//select한 mDTO를 LPanel에 뿌리기
		PstmtMemberLeftPanel plp = md.getMlp();
		
		plp.getJtfNum().setText(String.valueOf(mDTO.getNum()));
		plp.getJtfAge().setText(String.valueOf(mDTO.getAge()));
		plp.getJtfName().setText(mDTO.getName());
		plp.getJtfGender().setText(mDTO.getGender());
		plp.getJtfTel().setText(mDTO.getTel());
		plp.getJtfInputDate().setText(new SimpleDateFormat("yyyy-MM-dd").format(mDTO.getInput_date()));
		
		
	}// searchOneMember

	/**
	 * 1.사용자가 입력한 값을 얻고 2. DB에 추가하고 결과 출력 3. 입력 초기화
	 * 
	 * 객체 하나를 더 거쳐서 코드가 길어지지만 어떤 값이 들어오는지 확실히 할 수 있다.
	 */
	public void addMember() throws NumberFormatException {
		PstmtMemberLeftPanel mlp = md.getMlp();
		// 1.사용자가 입력한 값을 얻고
		MemberDTO mDTO = new MemberDTO();
		mDTO.setName(mlp.getJtfName().getText());
		mDTO.setAge(Integer.parseInt((mlp.getJtfAge().getText().trim())));
		mDTO.setGender(mlp.getJtfGender().getText().trim());
		mDTO.setTel(mlp.getJtfTel().getText().trim());

		// 2. DB에 추가하고 결과 출력
		// 실패 상황
		String msg = mDTO.getName() + "님의 회원정보를 추가할 수 없습니다.";

		// 성공상황
		if (ms.addMember(mDTO)) {
			msg = mDTO.getName() + "님의 회원정보가 성공적으로 추가됐습니다.";
			searchAllmember(); // 추가된 리스트를 갱신한다.
		}
		JOptionPane.showMessageDialog(md, msg);

		// 입력칸 초기화
		resetInputField();
	}// addMember

	/**
	 * 수정 기능<br>
	 * 현 시점 Age, Tel만 수정 가능.<br>
	 * 
	 * @throws NumberFormatException
	 */
	public void modifyMember() throws NumberFormatException {
		PstmtMemberLeftPanel mlp = md.getMlp();

		// num이 비어 있는 상태에서 버튼 눌렀을 때.
		if (mlp.getJtfNum().getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(md, "회원을 먼저 선택 후 변경해주세요");
			return;
		} // end if

		// 1.사용자가 입력한 값을 얻는다.
		MemberDTO mDTO = new MemberDTO();
		mDTO.setNum(Integer.parseInt((mlp.getJtfNum().getText().trim())));
		mDTO.setAge(Integer.parseInt((mlp.getJtfAge().getText().trim())));
		mDTO.setTel(mlp.getJtfTel().getText().trim());

		// 2. DB로 update SQL문을 보냄.
		int flag = ms.modifyMember(mDTO); // 결과 코드값 리턴.
		String outputMsg = "문제가 발생하였습니다. 잠시 후 다시 시도해주세요";

		switch (flag) {
		case 0:
			outputMsg = mDTO.getNum() + "번 회원은 존재하지 않습니다.";
			break;
		// 일단 num은 사용자 시점에서 변경이 안되니 현 상황에선 나오기 어렵다.
		case 1:
			outputMsg = mDTO.getNum() + "번 회원정보를 변경하였습니다.";
			break;
		}// end switch

		JOptionPane.showMessageDialog(md, outputMsg);

		// 변경된 데이터를 바로 갱신.
		searchAllmember();

		// 입력칸 초기화
		resetInputField();

	}// modifyMember

	/**
	 * 삭제 버튼의 동작.
	 * 
	 * @throws NumberFormatException
	 */
	public void removeMember() throws NumberFormatException {
		PstmtMemberLeftPanel mlp = md.getMlp();

		// num이 비어 있는 상태에서 버튼 눌렀을 때.
		if (mlp.getJtfNum().getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(md, "회원을 먼저 선택 후 삭제해주세요");
			return;
		} // end if

		// 1.사용자가 선택한 값을 얻는다.
		// 회원번호만 구하면 되니까 DTO는 패스
		int selectedNum = (Integer.parseInt((mlp.getJtfNum().getText().trim())));

		// 정말 삭제하시겠습니까? 파트.
		switch (JOptionPane.showConfirmDialog(md, selectedNum + "번 회원 정보를 정말 삭제하시겠습니까?")) {
		case JOptionPane.OK_OPTION:
			break;
		case JOptionPane.CANCEL_OPTION:
		case JOptionPane.NO_OPTION:
		default:
			return;
		}// end switch

		// 2. 삭제 작업 수행
		int flag = ms.removeMember(selectedNum); // 결과 코드값 리턴.
		String outputMsg = "문제가 발생하였습니다. 잠시 후 다시 시도해주세요";

		switch (flag) {
		case 0:
			outputMsg = selectedNum + "번 회원은 존재하지 않습니다.";
			break;
		// 일단 num은 사용자 시점에서 변경이 안되니 현 상황에선 나오기 어렵다.
		case 1:
			outputMsg = selectedNum + "번 회원정보를 삭제하였습니다.";
			break;

		}// end switch

		JOptionPane.showMessageDialog(md, outputMsg);

		// 변경된 데이터를 바로 갱신.
		searchAllmember();

		// 입력칸 초기화
		resetInputField();
	}// removeMember

}// class
