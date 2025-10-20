package day1020;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 * JTabbedPane의 사용<br>
 * 탭을 제공하는 컴포넌트<br>
 */
public class UseJTabbedPane extends JFrame implements ActionListener {

	private JPanel jpCenter, jpA, jpB, jpC;

	public UseJTabbedPane() {

		super("JTabbedPane");

		jpCenter = new JPanel();// 카드레이아웃을 적용할 패널 생성
		
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
	
	
		//★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆
		//탭을 제공하는 컴포넌트를 생성
		JTabbedPane jtp = new JTabbedPane();
		
		//탭에 패널에 등록
		jtp.add("개인회원정보",jpA);
		jtp.add("기업회원정보",jpB);
		jtp.add("외국인회원정보",jpC);
		
		//여러 개의 패널을 가진 탭을 배치
		add("Center",jtp);
		//★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆
		
		//이벤트 등록
		//창 설정
		setBounds(100,100,500,300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	}// UseCardLayout

	@Override
	public void actionPerformed(ActionEvent ae) {
		//버튼 이벤트 비교처리
		//버튼이 눌리면 메인 패널에 보여질 등록해둔 Paenl의 아이디를 설정한다
		
		
	}//actionPerformed

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new UseJTabbedPane();
	}// main

}// class
