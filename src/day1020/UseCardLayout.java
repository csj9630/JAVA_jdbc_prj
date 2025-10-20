package day1020;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * Card Layout의 사용<br>
 * 버튼이 눌릴 때마다 화면이 바뀌게 한다.<br>
 */
public class UseCardLayout extends JFrame implements ActionListener {

	private JButton jbtnA, jbtnB, jbtnC;
	private JPanel jpNorth, jpCenter, jpA, jpB, jpC;
	private CardLayout cl;// 이벤트가 생기면 화면을 바꿈

	public UseCardLayout() {

		super("카드레이아웃");

		jbtnA = new JButton("디자인 A");
		jbtnB = new JButton("디자인 B");
		jbtnC = new JButton("디자인 C");

		jpNorth = new JPanel();
		jpNorth.add(jbtnA);
		jpNorth.add(jbtnB);
		jpNorth.add(jbtnC);

		jpCenter = new JPanel();// 카드레이아웃을 적용할 패널 생성
		cl = new CardLayout();// 카드레이아웃을 생성
		jpCenter.setLayout(cl);// 패널 등록
		
		//패널 A 디자인
		jpA = new JPanel();//패널을 상속 받은 자식 클래스
		jpA.add(new JLabel("패널A"));
		jpA.add(new JButton("클릭"));

		//패널 B
		jpB = new JPanel();
		jpB.setLayout(new GridLayout(3,1));
		jpB.add(new JTextField("이름"));
		jpB.add(new JTextField("나이"));
		jpB.add(new JButton("클릭"));
		
		//패널 C
		jpC = new JPanel();
		jpC.add(new JLabel("이름"));
		jpC.add(new JTextField(10));
		jpC.add(new JButton("입력"));
		
		//테두리 타이틀 설정
		jpA.setBorder(new TitledBorder("입력A"));
		jpB.setBorder(new TitledBorder("입력B"));
		jpC.setBorder(new TitledBorder("입력C"));
	
	
		//메인 패널에 등록
		jpCenter.add(jpA,"inputA");//패널 객체, 패널 ID를 메인 패널에 등록
		jpCenter.add(jpB,"inputB");
		jpCenter.add(jpC,"inputC");
	
		//처음 보여줄 패널을 설정한다
		cl.show(jpCenter,"inputA");
		
		//패널 추가.
		add("North",jpNorth);
		add("Center",jpCenter);
		
		
		//이벤트 등록
		jbtnA.addActionListener(this);
		jbtnB.addActionListener(this);
		jbtnC.addActionListener(this);
	
		//창 설정
		setBounds(100,100,500,300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}// UseCardLayout

	@Override
	public void actionPerformed(ActionEvent ae) {
		//버튼 이벤트 비교처리
		//버튼이 눌리면 메인 패널에 보여질 등록해둔 Paenl의 아이디를 설정한다
		
		if(ae.getSource() == jbtnA) {
			cl.show(jpCenter, "inputA");
		}//end if
		
		if(ae.getSource() == jbtnB) {
			cl.show(jpCenter, "inputB");
		}//end if
		
		if(ae.getSource() == jbtnC) {
			cl.show(jpCenter, "inputC");
		}//end if
		
		
		
	}//actionPerformed

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new UseCardLayout();
	}// main

}// class
