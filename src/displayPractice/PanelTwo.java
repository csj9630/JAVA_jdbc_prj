package displayPractice;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelTwo extends JPanel {
	private JButton jbtnSelect;
	public PanelTwo() {
		ImageIcon img = new ImageIcon("C:\\Users\\user1\\Pictures\\bike.jpg");
		JLabel jlImg = new JLabel(img);
		

		jbtnSelect = new JButton("선택");
		
		setLayout(new GridLayout(2,1));
		add(jlImg);
		add(jbtnSelect);
		
	}//PanelTwo
	public JButton getJbtnSelect() {
		return jbtnSelect;
	}
	
}//class
