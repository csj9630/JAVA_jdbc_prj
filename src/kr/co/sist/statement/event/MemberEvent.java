package kr.co.sist.statement.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import kr.co.sist.statement.design.MemberDesign;
import kr.co.sist.statement.design.MemberLeftPanel;
import kr.co.sist.statement.dto.MemberDTO;
import kr.co.sist.statement.service.MemberService;

public class MemberEvent extends WindowAdapter implements ActionListener, /* ListSelectionListener, */MouseListener {

	private MemberDesign md;// MemberDesign 클래스와 HasA 관계
	private MemberService ms; //

	public MemberEvent(MemberDesign md) {
		// hasA 관계랑
		this.md = md;
		ms = new MemberService(); // MemberService 객체를 생성.
	}// MemberEvent

//	private boolean changeFlag;//클릭 한번에 두번 호출되는 걸 막기 위한 불리언변수, 초기값 false
//	@Override
//	public void valueChanged(ListSelectionEvent e) {
//		if(changeFlag) {
////			System.out.println("dddd");
////			useSplit();//DB를 사용하지 않는 방식
//			searchOneMember();//DB연동하는 방식.
//		}
//		changeFlag = !changeFlag;//현재 상태 반전.
//	}//valueChanged

	/**
	 * DB 연동해서 쓰는 방식.
	 */
	public void searchOneMember() {

		DefaultListModel<String> dlm = md.getMrp().getDlmMember();// 우측 패널의 리스트 모델을 가져옴
		JList<String> jl = md.getMrp().getJlMember();// 우측패널의 리스트를 가져옴
		String selectedMember = dlm.elementAt(jl.getSelectedIndex());// 선택된 인덱스 번호의 내용을 가져옴.
		String[] memberArrData = selectedMember.split(",");// spilt으로 나눠서 배열에 넣음.

		// 회원의 번호를 얻어보자
		int memberNum = Integer.parseInt(memberArrData[0]);
		MemberDTO mDTO = ms.searchOneMember(memberNum); // 선택한 회원 번호로 검색.
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy a", Locale.UK);

		// 배열의 데이터를 JTextField에 설정
		MemberLeftPanel mlp = md.getMlp();
		mlp.getJtfNum().setText(String.valueOf(mDTO.getNum()));
		mlp.getJtfName().setText(mDTO.getName());
		mlp.getJtfAge().setText(String.valueOf(mDTO.getAge()));
		mlp.getJtfGender().setText(mDTO.getGender());
		mlp.getJtfTel().setText(mDTO.getTel());
		mlp.getJtfInputDate().setText(sdf.format(mDTO.getInput_date()));

	}

	/**
	 * ㅁ List 내용 출력 방법1<br>
	 * DB에서 받은 레코드값은 List에 저장되어 있다<br>
	 * 선택한 index의 List 내용을 String으로 가져와서 split하여<br>
	 * 화면 JTF에 setText한다.<br>
	 * DB를 사용하지 않는 방식
	 * 
	 */
	public void useSplit() {
		DefaultListModel<String> dlm = md.getMrp().getDlmMember();// 우측 패널의 리스트 모델을 가져옴
		JList<String> jl = md.getMrp().getJlMember();// 우측패널의 리스트를 가져옴
		String selectedMember = dlm.elementAt(jl.getSelectedIndex());// 선택된 인덱스 번호의 내용을 가져옴.

		String[] memberArrData = selectedMember.split(",");// spilt으로 나눠서 배열에 넣음.

		// 배열의 데이터를 JTextField에 설정
		MemberLeftPanel mlp = md.getMlp();
		mlp.getJtfNum().setText(memberArrData[0]);
		mlp.getJtfName().setText(memberArrData[1]);
		mlp.getJtfAge().setText(memberArrData[2]);
		mlp.getJtfGender().setText(memberArrData[3]);
		mlp.getJtfTel().setText(memberArrData[4]);
		mlp.getJtfInputDate().setText(memberArrData[5]);

	}// useSplit

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		md.dispose();
	}

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

	}// actionPerformed

	/**
	 * 1.사용자가 입력한 값을 얻고 2. DB에 추가하고 결과 출력 3. 입력 초기화
	 * 
	 * 객체 하나를 더 거쳐서 코드가 길어지지만 어떤 값이 들어오는지 확실히 할 수 있다.
	 */
	public void addMember() throws NumberFormatException {
		MemberLeftPanel mlp = md.getMlp();
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
		
		//입력칸 초기화
		resetInputField();



	}// addMember

	/**
	 * 모든 회원을 검색하여 dlm에 설정한다.
	 */
	public void searchAllmember() {
		List<String> listMemberData = ms.searchAllMember();

		DefaultListModel<String> dlm = md.getMrp().getDlmMember();
		dlm.clear();// 리스트모델을 초기화
		
		
		if(listMemberData.isEmpty()) {//검색 결과가 없으면
			dlm.addElement("가입된 회원 정보가 존재하지 않습니다.");
		}//end if
		//이 때 선택하면 에러
		
		
		for (String recordData : listMemberData) {
			dlm.addElement(recordData);// 리스트 모델에 조회결과를 추가.
		} // end for

	}// searchAllmember

	/**
	 * 수정 기능<br>
	 * 현 시점 Age, Tel만 수정 가능.<br>
	 * 
	 * @throws NumberFormatException
	 */
	public void modifyMember() throws NumberFormatException {
		MemberLeftPanel mlp = md.getMlp();

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
		
		//입력칸 초기화
		resetInputField();

	}// modifyMember

	/**
	 * 삭제 버튼의 동작.
	 * @throws NumberFormatException
	 */
	public void removeMember() throws NumberFormatException {
		MemberLeftPanel mlp = md.getMlp();

		// num이 비어 있는 상태에서 버튼 눌렀을 때.
		if (mlp.getJtfNum().getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(md, "회원을 먼저 선택 후 삭제해주세요");
			return;
		} // end if

		// 1.사용자가 선택한 값을 얻는다.
		// 회원번호만 구하면 되니까 DTO는 패스
		int selectedNum = (Integer.parseInt((mlp.getJtfNum().getText().trim())));


		//정말 삭제하시겠습니까? 파트.
		switch (JOptionPane.showConfirmDialog(md, selectedNum + "번 회원 정보를 정말 삭제하시겠습니까?")) {
		case JOptionPane.OK_OPTION: 
			break;
		case JOptionPane.CANCEL_OPTION:
		case JOptionPane.NO_OPTION:
		default:
			return;
		}//end switch


		
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
		
		//입력칸 초기화
		resetInputField();

	}// removeMember
	
	
	
	/**
	 * 모든 버튼 동작 후, 입력 칸을 비워준다.
	 */
	private void resetInputField() {
		MemberLeftPanel mlp = md.getMlp();
		//입력칸을 초기화
		
		mlp.getJtfNum().setText("");
		mlp.getJtfName().setText("");
		mlp.getJtfAge().setText("");
		mlp.getJtfGender().setText("");
		mlp.getJtfTel().setText("");
		mlp.getJtfInputDate().setText("");
		
		mlp.getJtfName().requestFocus();
	}//resetInputField
	
	

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
				
			}catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(md, "회원정보가 없습니다.");
			}//end catch

		}// end switch
	}//mouseClicked

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

}
