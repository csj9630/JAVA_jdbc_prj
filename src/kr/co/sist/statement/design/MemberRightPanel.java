package kr.co.sist.statement.design;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import kr.co.sist.statement.event.MemberEvent;

/**
 * 우측 패널,  JList 세팅.
 */
public class MemberRightPanel extends JPanel {
	private DefaultListModel<String> dlmMember;
	private JList<String> jlMember;
	private JScrollPane jspJlMember;
	private MemberEvent me;
	
	
	/**
	 * 생성자에서 이벤트 클래스 받으면 꼬인다.
	 * 별도의 메서드로 함. 
	 */
	public MemberRightPanel() {
		dlmMember = new DefaultListModel<String>();
		
		jlMember = new JList<String>(dlmMember);
		
		jlMember.setFont(new Font("맑은 고딕",Font.BOLD,22));
		setBorder(new TitledBorder("회원정보"));
		setLayout(new BorderLayout());//BorderLayout으로 꽉 채운다.
		
		jspJlMember = new JScrollPane(jlMember);
		
		add(jspJlMember);
		
		
		
		
	}//MemberRightPanel
	
	/**
	 * 이벤트 처리객체를 받은 후 , JLIST Component의 이벤트 처리를 등록한다.<br>
	 * (이벤트 종류가 많으면 따로 분류하자.)<br>
	 *  모든 회원을 dlm에  설정하는 일<br>
	 * @param me
	 */
	public void setMemberEvent(MemberEvent me) {
		this.me = me;
		
		//이벤트처리
//		jlMember.addListSelectionListener(me);//clear도 인식해버려서 못 쓴다.
		jlMember.addMouseListener(me);//마우스 인식으로 변경.
		
		//모든 회원을 dlm에  설정하는 일.(이벤트 등록 후 발동되어야 한다)
		me.searchAllmember();
	}//setMemberEvent

	public DefaultListModel<String> getDlmMember() {
		return dlmMember;
	}

	public JList<String> getJlMember() {
		return jlMember;
	}

	public JScrollPane getJspJlMember() {
		return jspJlMember;
	}

}
