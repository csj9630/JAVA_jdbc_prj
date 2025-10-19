package day1014;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Work1013Design extends JFrame{
	
	
	private JTextField jtfNum, jtfName, jtfEmail,jtfPhone, jtfZipcode, jtfAddr, jtfAddrDetail;
	private JTextArea jtaInform;
	private JButton jbtnImage, jbtnAdd, jbtnSearch, jbtnClose, jbtnZipSearch;



	private JLabel jlbImage;
	
	public Work1013Design() {
		super("친구관리");
		
		JLabel jlbNum = new JLabel("번호");
		JLabel jlbName = new JLabel("이름");
		JLabel jlbEmail = new JLabel("이메일");
		JLabel jlbPhone = new JLabel("전화번호");
		JLabel jlbZipcode = new JLabel("우편번호");
		JLabel jlbAddr = new JLabel("주소");
		JLabel jlbAddrDetail = new JLabel("상세주소");
		
		
		JLabel jlbInform = new JLabel("소개");
		
		ImageIcon ii= new ImageIcon("C:/dev/workspace/jdbc_prj/src/day1014/images/rs_default_img.png");
		jlbImage = new JLabel(ii);
		jtfNum = new JTextField();
		jtfName = new JTextField();
		jtfEmail = new JTextField();
		jtfPhone = new JTextField();
		jtfZipcode = new JTextField();
		jtfAddr = new JTextField();
		jtfAddrDetail = new JTextField();
		
		jtaInform = new JTextArea();
		
		jbtnImage = new JButton("선택");
		jbtnAdd = new JButton("추가");
		jbtnSearch = new JButton("검색");
		jbtnClose = new JButton("종료");
		jbtnZipSearch = new JButton("검색");
		JScrollPane jspInform = new JScrollPane(jtaInform);
			
		Font font = new Font("맑은고딕",Font.BOLD,24);
		
		
		setLayout(null);
		
		
		jlbNum.setSize(200,50);
		jlbNum.setLocation(30,20);
		jlbNum.setFont(font);
		add(jlbNum);
		
		jtfNum.setSize(200,50);
		jtfNum.setLocation(150,20);
		jtfNum.setFont(font);
		add(jtfNum);
		jlbName.setSize(200,50);
		jlbName.setLocation(30,80);
		jlbName.setFont(font);
		add(jlbName);
		
		jtfName.setSize(200,50);
		jtfName.setLocation(150,80);
		jtfName.setFont(font);
		add(jtfName);
		
		jlbEmail.setSize(200,50);
		jlbEmail.setLocation(30,140);
		jlbEmail.setFont(font);
		add(jlbEmail);
		
		jtfEmail.setSize(200,50);
		jtfEmail.setLocation(150,140);
		jtfEmail.setFont(font);
		add(jtfEmail);
		
		jlbPhone.setSize(200,50);
		jlbPhone.setLocation(30,200);
		jlbPhone.setFont(font);
		add(jlbPhone);
		
		jtfPhone.setSize(200,50);
		jtfPhone.setLocation(150,200);
		jtfPhone.setFont(font);
		add(jtfPhone);

		
		jlbZipcode.setSize(200,50);
		jlbZipcode.setLocation(30,260);
		jlbZipcode.setFont(font);
		add(jlbZipcode);
		
		jtfZipcode.setSize(200,50);
		jtfZipcode.setLocation(150,260);
		jtfZipcode.setFont(font);
		add(jtfZipcode);
		
		jlbAddr.setSize(200,50);
		jlbAddr.setLocation(30,320);
		jlbAddr.setFont(font);
		add(jlbAddr);
		
		jtfAddr.setSize(400,50);
		jtfAddr.setLocation(150,320);
		jtfAddr.setFont(font);
		add(jtfAddr);
		
		
		jlbAddrDetail.setSize(200,50);
		jlbAddrDetail.setLocation(30,380);
		jlbAddrDetail.setFont(font);
		add(jlbAddrDetail);
		
		jtfAddrDetail.setSize(400,50);
		jtfAddrDetail.setLocation(150,380);
		jtfAddrDetail.setFont(font);
		add(jtfAddrDetail);
		
		
		
		jbtnZipSearch.setSize(170,50);
		jbtnZipSearch.setLocation(380, 260);
		jbtnZipSearch.setFont(font);
		add(jbtnZipSearch);
		
		jlbInform.setSize(200,50);
		jlbInform.setLocation(30,430);
		jlbInform.setFont(font);
		add(jlbInform);
		
		jspInform.setSize(400,120);
		jspInform.setLocation(150,440);
		jtaInform.setFont(font);
		add(jspInform);
		
		jlbImage.setSize(170, 170);
		jlbImage.setLocation(380, 20);
		jlbImage.setFont(font);
		jlbImage.setBackground(Color.BLUE);
		jlbImage.setOpaque(true);
		jlbImage.setHorizontalAlignment(SwingConstants.CENTER);
		add(jlbImage);
		
		jbtnImage.setSize(170,50);
		jbtnImage.setLocation(380, 200);
		jbtnImage.setFont(font);
		add(jbtnImage);
		
		jbtnAdd.setSize(120,50);
		jbtnAdd.setLocation(190,600);
		jbtnAdd.setFont(font);		
		add(jbtnAdd);
		
		jbtnSearch.setSize(120,50);
		jbtnSearch.setLocation(320,600);
		jbtnSearch.setFont(font);		
		add(jbtnSearch);
		
		jbtnClose.setSize(120,50);
		jbtnClose.setLocation(450,600);
		jbtnClose.setFont(font);
		add(jbtnClose);
		
		
		//이벤트 등록
		Work1013Evt we = new Work1013Evt(this);
		jbtnAdd.addActionListener(we);		
		jbtnClose.addActionListener(we);		
		jbtnSearch.addActionListener(we);		
		jbtnImage.addActionListener(we);		
		jbtnZipSearch.addActionListener(we);		
		
		
//		addWindowListener(we);
		
		setBounds(300,300,1000,800);
		setVisible(true);
		
	}


	public JLabel getJlbImage() {
		return jlbImage;
	}

	public JTextField getJtfNum() {
		return jtfNum;
	}


	public JTextField getJtfName() {
		return jtfName;
	}


	public JTextField getJtfEmail() {
		return jtfEmail;
	}


	public JTextField getJtfPhone() {
		return jtfPhone;
	}


	public JTextArea getJtaInform() {
		return jtaInform;
	}


	public JButton getJbtnImage() {
		return jbtnImage;
	}


	public JButton getJbtnAdd() {
		return jbtnAdd;
	}


	public JButton getJbtnSearch() {
		return jbtnSearch;
	}


	public JButton getJbtnClose() {
		return jbtnClose;
	}

	
	public JButton getJbtnZipSearch() {
		return jbtnZipSearch;
	}

	public JTextField getJtfZipcode() {
		return jtfZipcode;
	}


	public JTextField getJtfAddr() {
		return jtfAddr;
	}


	public JTextField getJtfAddrDetail() {
		return jtfAddrDetail;
	}
	
	

}
