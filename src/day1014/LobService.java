package day1014;

import java.io.IOException;
import java.sql.SQLException;

public class LobService {
	public boolean addFriends(LobDTO lDTO) {
		boolean flag = false;

		LobDAO lDAO = LobDAO.getInstance();
		try {
			lDAO.insertFriendsMgr(lDTO);
			flag = true;
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}// addFriends

	public LobDTO searchFriends(int num) {
		LobDTO lDTO = null;

		try {
			LobDAO lDAO = LobDAO.getInstance();
			lDTO = lDAO.selectFriends(num);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lDTO;
	}//searchFriends
}// class
