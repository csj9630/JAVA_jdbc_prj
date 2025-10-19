package day1016;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import day1014.Work1013Design;

public class ZipcodeFrm extends JDialog {

	private JTextField jtfDong;
	private JButton jbtnSearch;
	private JTable jtaZipcode;
	private DefaultTableModel dtmZipcode;
	private Work1013Design wd;
	
	public ZipcodeFrm(Work1013Design wd) {
		super(wd,"우편번호 검색");
		this.wd = wd;
		
		JLabel jlblDong=new JLabel("동이름");
		jtfDong=new JTextField(10);
		jbtnSearch=new JButton("검색");
		
		JPanel jpNorth=new JPanel();
		jpNorth.add(jlblDong);
		jpNorth.add(jtfDong);
		jpNorth.add(jbtnSearch);
		
		jpNorth.setBorder(new TitledBorder("검색폼"));
		
		String[] columnNames= {"우편번호","주소"};
		dtmZipcode=new DefaultTableModel( columnNames,0);
		jtaZipcode=new JTable( dtmZipcode );
		
		jtaZipcode.getColumnModel().getColumn(0).setPreferredWidth(60);
		jtaZipcode.getColumnModel().getColumn(1).setPreferredWidth(350);
		
		JScrollPane jsp=new JScrollPane(jtaZipcode);
		jsp.setBorder(new TitledBorder("검색결과"));
		add("North", jpNorth);
		add("Center",jsp);
		
		//이벤트 등록
		ZipcodeEvt ze = new ZipcodeEvt(this);
		jbtnSearch.addActionListener(ze);
		jtaZipcode.addMouseListener(ze);
		jtfDong.addActionListener(ze);//엔터키 액션 인식을 위해
		addWindowFocusListener(ze);
		
		
		setBounds(wd.getX()+100 , wd.getY()+200, 550, 600);
		
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
	}

	public JTextField getJtfDong() {
		return jtfDong;
	}

	public JButton getJbtnSearch() {
		return jbtnSearch;
	}

	public JTable getJtaZipcode() {
		return jtaZipcode;
	}

	public DefaultTableModel getDtmZipcode() {
		return dtmZipcode;
	}

	public Work1013Design getWd() {
		return wd;
	}
	
	
	
}
