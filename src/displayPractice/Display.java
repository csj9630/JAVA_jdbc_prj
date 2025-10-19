package displayPractice;

import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Display extends JFrame {
	private PanelOne p1;
	private PanelTwo p2;

	public Display() {
		super("화면 만들기");
		
		JPanel jpCenter = new JPanel();
		p1 = new PanelOne();
		p2 = new PanelTwo();
		
		jpCenter.setLayout(new GridLayout(1,2));
		
		jpCenter.add(p1);
		jpCenter.add(p2);
		
		add("Center",jpCenter);
		
		setBounds(300,300,700,400);
		setVisible(true);
	}
	
}//class
