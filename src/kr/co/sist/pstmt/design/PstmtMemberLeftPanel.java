package kr.co.sist.pstmt.design;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * 사원정보를 입력하는 패널
 * 화면이 많아질 경우 별도의 클래스에서 JPanel을 상속 받아서 코드 작성한다.
 * 일단 패널에서 필요한 코드를 싹 다 넣어두자.
 * 이벤트는 별도 클래스에서 처리.
 */
public class PstmtMemberLeftPanel extends JPanel {
	private JTextField jtfNum, jtfName, jtfAge, jtfGender, jtfTel, jtfInputDate;
	public PstmtMemberLeftPanel() {

		//폰트 지정
		Font font = new Font("맑은 고딕", Font.BOLD, 22);
		
		//JLabal과 컨텐츠 세팅, 폰트 적용
		JLabel jlblNum = new JLabel("쌀번호");
		jtfNum = new JTextField();
		jlblNum.setFont(font);
		jtfNum.setFont(font);
		jtfNum.setEditable(false);//수정불가
		
		JLabel jlblName = new JLabel("고객명");
		jtfName = new JTextField();
		jlblName.setFont(font);
		jtfName.setFont(font);
		
		JLabel jlblAge = new JLabel("나이");
		jtfAge = new JTextField();
		jlblAge.setFont(font);
		jtfAge.setFont(font);
		
		JLabel jlblGender = new JLabel("성별");
		jtfGender = new JTextField();
		jlblGender.setFont(font);
		jtfGender.setFont(font);
		
		JLabel jlblTel = new JLabel("전화번호");
		jtfTel = new JTextField();
		jlblTel.setFont(font);
		jtfTel.setFont(font);
		
		JLabel jlblInputDate = new JLabel("입력일");
		jtfInputDate = new JTextField();
		jtfInputDate.setEditable(false);//수정불가
		jlblInputDate.setFont(font);
		jtfInputDate.setFont(font);
		
		//레이아웃 세팅
		setLayout(new GridLayout(6,2));
		
		//라벨을 화면에 올림
		add(jlblNum);
		add(jtfNum);
		add(jlblName);
		add(jtfName);
		add(jlblAge);
		add(jtfAge);
		add(jlblGender);
		add(jtfGender);
		add(jlblTel);
		add(jtfTel);
		add(jlblInputDate);
		add(jtfInputDate);
		
		setBorder(new TitledBorder("고객정보입력"));
		
		
	}//MemberLeftPanel
	public JTextField getJtfNum() {
		return jtfNum;
	}
	public JTextField getJtfName() {
		return jtfName;
	}
	public JTextField getJtfAge() {
		return jtfAge;
	}
	public JTextField getJtfGender() {
		return jtfGender;
	}
	public JTextField getJtfTel() {
		return jtfTel;
	}
	public JTextField getJtfInputDate() {
		return jtfInputDate;
	}
	
	

}//class
