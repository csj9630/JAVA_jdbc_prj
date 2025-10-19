package day1016;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ZipcodeService {
	public List<ZipcodeDTO> searchStmtZipcode(String dong){
		List<ZipcodeDTO> list = new ArrayList<ZipcodeDTO>();
		
		ZipcodeDAO zDAO = ZipcodeDAO.getInstance();
		
		try {
			list = zDAO.useStmtSelectZipcode(dong);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}//searchStmtZipcode

	
	public List<ZipcodeDTO> searchPstmtZipcode(String dong){
		List<ZipcodeDTO> list = new ArrayList<ZipcodeDTO>();
		
		ZipcodeDAO zDAO = ZipcodeDAO.getInstance();
		
		try {
			list = zDAO.usePstmtSelectZipcode(dong);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}//searchPstmtZipcode
}
