package displayPractice;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class PanelOne extends JPanel {
	private JTextArea jtaNum, jtaName, jtaEmail, jtaTel;
	
	public PanelOne() {
		// 폰트 지정
//		Font font = new Font("맑은 고딕", Font.BOLD, 22);

		JLabel jlblNum = new JLabel("번호"); 
		jlblNum.setBorder(BorderFactory.createLineBorder(Color.black));
		jtaNum = new JTextArea();
		jtaNum.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JLabel jlblName = new JLabel("이름"); 
		jlblName.setBorder(BorderFactory.createLineBorder(Color.black));
		jtaName = new JTextArea();
		jtaName.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JLabel jlblEmail = new JLabel("EMail"); 
		jlblEmail.setBorder(BorderFactory.createLineBorder(Color.black));
		jtaEmail = new JTextArea();
		jtaEmail.setBorder(BorderFactory.createLineBorder(Color.black));
		
		JLabel jlblTel = new JLabel("전화번호");
		jlblTel.setBorder(BorderFactory.createLineBorder(Color.black));
		jtaTel = new JTextArea();
		jtaTel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		setLayout(new GridLayout(4,2));
		
		add(jlblNum);
		add(jtaNum);
		add(jlblName);
		add(jtaName);
		add(jlblEmail);
		add(jtaEmail);
		add(jlblTel);
		add(jtaTel);
		
		setBorder(new TitledBorder("정보 입력"));
		
		
	}// PanelOne

	public JTextArea getJtaNum() {
		return jtaNum;
	}

	public JTextArea getJtaName() {
		return jtaName;
	}

	public JTextArea getJtaEmail() {
		return jtaEmail;
	}

	public JTextArea getJtaTel() {
		return jtaTel;
	}

}// class
