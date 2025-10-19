package kr.co.sist.pstmt.design;

import javax.swing.JButton;
import javax.swing.JPanel;

import kr.co.sist.pstmt.event.PstmtMemberEvent;

public class PstmtMemberSouthPanel extends JPanel {
	private JButton jbtnAdd, jbtnModify, jbtnRemove, jbtnClose;
	private PstmtMemberEvent me;

	public PstmtMemberSouthPanel() {
		jbtnAdd = new JButton("추가");
		jbtnModify= new JButton("수정");
		jbtnRemove= new JButton("삭제");
		jbtnClose= new JButton("종료");
		add(jbtnAdd);
		add(jbtnModify);
		add(jbtnRemove);
		add(jbtnClose);
	}

	//이벤트 처리 객체 생성이 된 후 has관계가 되어야 이벤트 비교가 된다.
	
	public void setMemberEvent(PstmtMemberEvent me) {
		this.me = me;
	}
	/**
	 * 버튼에 이벤트를 등륵하는 일. setMemberEvent이후에 호출되어야한다.
	 */
	public void addEvent() {
		jbtnAdd.addActionListener(me);
		jbtnModify.addActionListener(me);
		jbtnRemove.addActionListener(me);
		jbtnClose.addActionListener(me);
		
	}

	public JButton getJbtnAdd() {
		return jbtnAdd;
	}

	public JButton getJbtnModify() {
		return jbtnModify;
	}

	public JButton getJbtnRemove() {
		return jbtnRemove;
	}

	public JButton getJbtnClose() {
		return jbtnClose;
	}


}