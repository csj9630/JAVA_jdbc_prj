package day1014;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import day1016.ZipcodeFrm;
import kr.co.sist.util.img.ImageResize;

public class Work1013Evt extends WindowAdapter implements ActionListener {

	private Work1013Design wd;
	private String selectedImg;

	public Work1013Evt(Work1013Design wd) {
		this.wd = wd;
		selectedImg = "";
	}// Work1013Evt

	public void prviewImg() {
		JFileChooser jfc = new JFileChooser();
		jfc.showOpenDialog(wd);

		File file = jfc.getSelectedFile();
		if (file != null) {
			// 선택한 파일 사이즈를 200x170으로 변경 후 이미지 아이콘에 설정함
			String fileName = file.getName();

			String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

//			이미지는 png,jpg,gif,bmp만 사용
			String allowedExt = "png,jpg,gif,bmp";

			String[] allowExtArr = allowedExt.split(",");

			boolean flag = false;

			for (String aExt : allowExtArr) {
				if (flag = ext.equals(aExt)) {
					break;
				} // end if
			} // end for

			if (!flag) {
				JOptionPane.showMessageDialog(wd, "이미지 확장자가 아닙니다.");
				return;
			} // end if

			// 이미지 크기 변경
			ImageResize.resizeImage(file.getAbsolutePath(), 200, 170);

			selectedImg = file.getParent() + "/" + "rs_" + file.getName();
			System.out.println(selectedImg);

			wd.getJlbImage().setIcon(new ImageIcon(selectedImg));

		} // end if

	}// prviewImg

	public void addFriends() throws IOException {
		// 이름 이메일 전화번호 인트로 이미지 5가지를 받아와서 DB에 추가 작업을 실행함.
		String name = wd.getJtfName().getText().trim();
		String email = wd.getJtfEmail().getText().trim();
		String tel = wd.getJtfPhone().getText().trim();
		String intro = wd.getJtaInform().getText().trim();

		// -----이미지 선택됐는지 체크---------
		if ("".equals(selectedImg)) {
			JOptionPane.showMessageDialog(wd, "이미지를 선택해주세요");
			return;
		} // end if

		File file = new File(selectedImg);
		FileInputStream fisImg = new FileInputStream(file);

		String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);

		LobDTO lDTO = new LobDTO(0, name, email, tel, intro, ext, fisImg, null, file);
		LobService ls = new LobService();
		String msg = "친구를 추가할 수 없습니다. 잠시 후 다시 시도해주세요!";
		if (ls.addFriends(lDTO)) {
			msg = "친구가 정상 추가되었습니다";

			// 크기 변경한 rs_이미지를 삭제하기

			// 참조 프로세스가 존재하면 파일이 삭제 안된다
			if (fisImg != null) {
				fisImg.close();
			} // end if
			lDTO.getFile().delete();
		} // end if

		JOptionPane.showMessageDialog(wd, msg);

		// 입력칸 초기화
		wd.getJtfName().setText("");
		wd.getJtfEmail().setText("");
		wd.getJtfPhone().setText("");
		wd.getJtaInform().setText("");
		ImageIcon ii = new ImageIcon("C:/dev/workspace/jdbc_prj/src/day1014/images/rs_default_img.jpg");
		wd.getJlbImage().setIcon(ii);
	}// friends

	public void searchFriends() {
		LobService ls = new LobService();
		String tempNum = wd.getJtfNum().getText();

		if (tempNum.isEmpty()) {
			JOptionPane.showMessageDialog(wd, "검색 번호가 비어있습니다.");
			return;
		} // end if

		try {
			int num = Integer.parseInt(tempNum);
			LobDTO lDTO = ls.searchFriends(num);// 친구조회

			if (lDTO == null) {
				JOptionPane.showMessageDialog(wd, num + "으로 검색된 친구는 존재하지 않습니다.");
				return;
			} // end if
			
			wd.getJtfName().setText(lDTO.getName());
			wd.getJtfEmail().setText(lDTO.getEmail());
			wd.getJtfPhone().setText(lDTO.getTel());
			wd.getJtaInform().setText(lDTO.getIntro());
			
			//이미지 넣기
			//Properties로 외부 저장소를 가져온다.
			Properties prop = new Properties();
			try {
				prop.load(new FileInputStream("C:\\dev\\workspace\\jdbc_prj\\src\\properties\\database.properties"));
				
				ImageIcon ii = new ImageIcon(prop.getProperty("savePath")+lDTO.getNum()+"."+lDTO.getExt());
				wd.getJlbImage().setIcon(ii);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//end catch
			
			File dir = new File(prop.getProperty("savePath"));
			
			
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(wd, "친구 번호는 숫자만 입력해주세요");
		} // end catch
	}// searchFriends

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == wd.getJbtnAdd()) {
			try {
				addFriends();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} // end if

		if (ae.getSource() == wd.getJbtnClose()) {
			wd.dispose();

		} // end if

		if (ae.getSource() == wd.getJbtnImage()) {
			prviewImg();
		} // end if

		if (ae.getSource() == wd.getJbtnSearch()) {
			searchFriends();
		} // end if
		
		if (ae.getSource() == wd.getJbtnZipSearch()) {
			new ZipcodeFrm(wd);
		} // end if
	}// actionPerformed

	@Override
	public void windowClosing(WindowEvent e) {
		wd.dispose();
	}// windowClosing

}// class
