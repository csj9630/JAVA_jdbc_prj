package kr.co.sist.statement.design;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import kr.co.sist.statement.event.MemberEvent;

/**
 * 메인 디자인 클래스
 */
public class MemberDesign extends JFrame{
	
	//디자인에 객체가 너무 많을 때 JPanel 상속 클래스를 만들어서 구역별로 따로 만들어서 합친다.
	private MemberLeftPanel mlp;
	private MemberRightPanel mrp;
	private MemberSouthPanel msp;
	
	public MemberDesign() {
		super("고객 관리");
		
		//Panel 세팅
		JPanel jpCenter = new JPanel();
		
		
		//디자인이 많아지면 별도의 클래스에서 JPanel을 상속받아서 디자인을 넣어둔다.
		mlp = new MemberLeftPanel();
		msp = new MemberSouthPanel();
		mrp = new MemberRightPanel();
		
		//이벤트처리클래스 hasA관계처리
		MemberEvent me = new MemberEvent(this);
		
		//이벤트 객체가 만들어진 후 각 패널의 이벤트리스너가 호출되게 조정.
		msp.setMemberEvent(me);//South Panel의 이벤트 등록
		msp.addEvent();
		mrp.setMemberEvent(me);//Right Panel의 이벤트 등록
		
		//얘는 순서가 좀 뒤죽박죽이라 에러 날 수도 있음.
		
		
		jpCenter.setLayout(new GridLayout(1,2));
		jpCenter.add(mlp);
		jpCenter.add(mrp);
		
		add("Center", jpCenter);
		add("South",msp);
		
		addWindowListener(me);//window 이벤트가 이 클래스가 있으니까.
		
		setBounds(300,300,800,300);
		setVisible(true);
		
		
	}//MemberDesign
	
	
	//getter 메서드로 이벤트처리.
	public MemberLeftPanel getMlp() {
		return mlp;
	}

	public MemberRightPanel getMrp() {
		return mrp;
	}

	public MemberSouthPanel getMsp() {
		return msp;
	}
	
}//class
