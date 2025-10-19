package day1016;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.channels.SelectableChannel;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import day1014.Work1013Design;

public class ZipcodeEvt extends WindowAdapter implements MouseListener, ActionListener {
	private ZipcodeFrm zf;
	private List<ZipcodeDTO> listZip;

	// 폼과 hasA관계
	public ZipcodeEvt(ZipcodeFrm zf) {
		this.zf = zf;
	}// ZipcodeEvt

	public void searchZipcode() {
		String dong = zf.getJtfDong().getText().trim();

		if (dong.isEmpty()) {
			JOptionPane.showMessageDialog(zf, "동은 필수입력이다");
			return;
		} // end if

		ZipcodeService zs = new ZipcodeService();

//		List<ZipcodeDTO> listZip = zs.searchStmtZipcode(dong);//stmt로 조회 //SQL Injection에 취약
		List<ZipcodeDTO> listZip = zs.searchPstmtZipcode(dong);//pstmt로 조회 //SQL Injection에 안전
		DefaultTableModel dtmZipcode = zf.getDtmZipcode();

		this.listZip = listZip;

		dtmZipcode.setRowCount(0);
		String[] rowData = null;

		StringBuilder tempAddr = new StringBuilder();
		for (ZipcodeDTO zDTO : listZip) {
			tempAddr.append(zDTO.getSido()).append(" ").append(zDTO.getGugun()).append(" ").append(zDTO.getDong())
					.append(" ").append(zDTO.getBunji());

			rowData = new String[2];
			rowData[0] = zDTO.getZipcode();
			rowData[1] = tempAddr.toString();

			dtmZipcode.addRow(rowData);

			// 검색한 동을 초기화
			tempAddr.delete(0, tempAddr.length());
		} // end for

		zf.getJtfDong().setText("");
		zf.getJtfDong().requestFocus();
	}// searchZipcode

	public void sendZipcode() {
		// 선택한 행의 zipdcode 값을 부모창으로 보내기
		int selectedRow = zf.getJtaZipcode().getSelectedRow();
		Work1013Design wd = zf.getWd();
		
		ZipcodeDTO zDTO = listZip.get(selectedRow);
		wd.getJtfZipcode().setText(zDTO.getZipcode());
		wd.getJtfAddr().setText(zDTO.getSido()+ " "+zDTO.getGugun()+ " " +zDTO.getDong());
		
		zf.dispose(); //자식창을 닫는다.
	
	}// sendZipcode

//-----------------------액션리스너------------------------------------

	@Override
	public void actionPerformed(ActionEvent e) {
		// 버튼 하나만 있으니 이벤트 비교는 패스
		// jtf 엔터키에도 반응한다.
		searchZipcode();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		sendZipcode();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		zf.dispose();
	}

}
