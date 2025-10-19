package kr.co.sist.pstmt.design;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import kr.co.sist.pstmt.event.PstmtMemberEvent;

/**
 * 우측 패널,  JList 세팅.
 */
public class PstmtMemberRightPanel extends JPanel {
	private DefaultTableModel dtmMember;
	private JTable jtMember;
	private JScrollPane jspJtMember;
	private PstmtMemberEvent me;
	
	
	/**
	 * 생성자에서 이벤트 클래스 받으면 꼬인다.
	 * 별도의 메서드로 함. 
	 */
	public PstmtMemberRightPanel() {
		
		String[] columnNames= {"번호", "나이","이름", "성별","전화번호","가입일" };
		dtmMember=new DefaultTableModel(columnNames,0);
		
		jtMember = new JTable(dtmMember);
		
		//자동배치인 컬럼의 사이즈를 직접 설정
		//column의 넓이 설정
		TableColumnModel tcm =  jtMember.getColumnModel(); //JTable의 ColumnModel을 가져옴
		tcm.getColumn(0).setPreferredWidth(40); // 0번째 컬럼(번호) width(40px)를 설정함.
		tcm.getColumn(1).setPreferredWidth(30); // 나이
		tcm.getColumn(2).setPreferredWidth(40); // 이름
		tcm.getColumn(3).setPreferredWidth(20); // 성별
		tcm.getColumn(4).setPreferredWidth(100); // 전화번호
		tcm.getColumn(5).setPreferredWidth(120); // 가입일
		
		//행의 높이 설정
		jtMember.setRowHeight(24);
		
		jtMember.setFont(new Font("맑은 고딕",Font.BOLD,12));
		setBorder(new TitledBorder("회원정보"));
		setLayout(new BorderLayout());//BorderLayout으로 꽉 채운다.
		
		jspJtMember = new JScrollPane(jtMember);
		
		add(jspJtMember);
		
		
		
		
	}//MemberRightPanel
	
	/**
	 * 이벤트 처리객체를 받은 후 , JLIST Component의 이벤트 처리를 등록한다.<br>
	 * (이벤트 종류가 많으면 따로 분류하자.)<br>
	 *  모든 회원을 dlm에  설정하는 일<br>
	 * @param me
	 */
	public void setMemberEvent(PstmtMemberEvent me) {
		this.me = me;
		
		//이벤트처리
		jtMember.addMouseListener(me);//마우스 인식으로 변경.
		
		//모든 회원을 dlm에  설정하는 일.(이벤트 등록 후 발동되어야 한다)
		me.searchAllmember();
	}//setMemberEvent

	public DefaultTableModel getDtmMember() {
		return dtmMember;
	}

	public JTable getJtMember() {
		return jtMember;
	}

	public JScrollPane getJspJtMember() {
		return jspJtMember;
	}


}//class
