package day1014;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import kr.co.sist.pstmt.dao.GetConnection;
import oracle.sql.CLOB;

public class LobDAO {

	private static LobDAO lDAO;

	private LobDAO() {
		super();
	}// LobDAO

	// 싱글톤 패턴으로 DAO 클래스 구성
	public static LobDAO getInstance() {
		if (lDAO == null) {
			lDAO = new LobDAO();
		} // end if
		return lDAO;
	}// getInstance

	public void insertFriendsMgr(LobDTO lDTO) throws SQLException, IOException {
		// DB 작업 스탭
		// 1.드라이버로딩
		// 2.커넥션얻기
		Connection con = null;
		PreparedStatement pstmt = null;

		GetConnection gc = GetConnection.getInstance();

		try {
			con = gc.getConn();
			con.setAutoCommit(false);//오토커밋 해제,
			// 3.쿼리문 생성객체 얻기
			String insertFriends = "insert into friends_mgr(NUM, NAME, EMAIL, TEL, INTRO, IMG,EXT) values (seq_friends.nextval,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(insertFriends);

			// 4. 바인드변수에 값설정
			pstmt.setString(1, lDTO.getName());
			pstmt.setString(2, lDTO.getEmail());
			pstmt.setString(3, lDTO.getTel());
			pstmt.setString(4, lDTO.getIntro());
			pstmt.setBinaryStream(5, lDTO.getImg(), lDTO.getFile().length());
			pstmt.setString(6, lDTO.getExt());

			// 5.쿼리문 수행 후 결과 얻기
			int cnt = pstmt.executeUpdate();

			if(cnt ==1 ) {//목표 실행수이면 커밋, 아니면 Rollback
				con.commit();
			}
		} finally {
			gc.dbClose(con, pstmt, null);
			// 6. 연결끊기
		} // end finally

	}// insertFriendsMgr

	/**
	 * BLOB 데이터를 읽어 들이기 위해서 스트림을 사용한다.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public LobDTO selectFriends(int num) throws IOException, SQLException {
		LobDTO lDTO = null;

		// 1.2. 커넥션 얻기

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		InputStream is = null;
		FileOutputStream fos = null;

		GetConnection gc = GetConnection.getInstance();

		try {
			con = gc.getConn();
			// 3.
			StringBuilder selectFriend = new StringBuilder();

			// sql문에 띄어쓰기 꼭 넣기
			selectFriend.append("SELECT NUM, NAME, EMAIL, TEL, INTRO, IMG,EXT, INPUT_DATE").append(" FROM FRIENDS_MGR")
					.append(" WHERE NUM=?");

			pstmt = con.prepareStatement(selectFriend.toString());

			// 4.
			pstmt.setInt(1, num);
			// 5.
			rs = pstmt.executeQuery();

			if (rs.next()) {
				lDTO = new LobDTO();
				lDTO.setNum(num); // 입력된 번호를 할당

				// DB Tables에서 조회된 결과를 설정.
				lDTO.setName(rs.getString("name"));
				lDTO.setEmail(rs.getString("email"));
				lDTO.setTel(rs.getString("tel"));
//				lDTO.setIntro(rs.getString("intro"));
				// clob : windows에서는 getString이 되지만 Linux에서는 안되니까 사용 자제.
				
				//제대로 하기
				
				// CLOB은 4Gbyte까지의 문자열을 저장할 수 있음으로 별도의 스트림을 연결하여
				// 데이터를 읽어들인다.
				StringBuilder intro = new StringBuilder();
				Clob clob = rs.getClob("intro");

				// IOException 있음
				if (clob != null) {
					BufferedReader br = null;
					try {
						br = new BufferedReader(clob.getCharacterStream());

						String temp = "";
						while ((temp = br.readLine()) != null) {// 읽어들인 값이 존재하면
							intro.append(temp).append("\n");
						} // end while

						if (br != null) {
							br.close();
						} // end if
						
						//읽은 값을 lDTO에 추가.
						lDTO.setIntro(intro.toString());
						
					} catch (IOException ie) {
						ie.printStackTrace();
					} // end catch
				} // end if

				lDTO.setExt(rs.getString("ext"));
				lDTO.setInputDate(rs.getDate("input_date"));

				// 이미지는 별도의 스트림을 연결하여 받아들인다.
				// Properties로 외부 저장소를 가져온다.
				Properties prop = new Properties();
				prop.load(new FileInputStream("C:\\dev\\workspace\\jdbc_prj\\src\\properties\\database.properties"));

				File dir = new File(prop.getProperty("savePath"));

				// 디렉터리가 없다면 생성한다.
				if (!dir.exists()) {
					dir.mkdir();
				} // end if

				// 다운로드된 파일명은 "PK값+확장자" 형식.
				File file = new File(dir.getAbsolutePath() + File.separator + lDTO.getNum() + "." + lDTO.getExt());

				// 출력스트림 설정
				fos = new FileOutputStream(file); // 파일이 존재하면 덮어쓰고, 존재하지 않으면 생성
				// ->회원 당 파일 하나로 관리 가능.

				// 입력스트림 얻기
				is = rs.getBinaryStream("img");
				int dataLength = 0;
				byte[] readData = new byte[512];

				while ((dataLength = is.read(readData)) != -1) { // 읽어들인 내용이 존재한
					// 읽어들인 내용의 길이까지를 출력스트림으로 출력
					fos.write(readData, 0, dataLength);
				} // end while
				fos.flush();

			} // end if
				// 6.
		} finally {
			if (is != null) {
				is.close();
			} // end if
			if (fos != null) {
				fos.close();
			} // end if
			gc.dbClose(con, pstmt, rs);
		} // end finally

		return lDTO;
	}// selectFriends
}// class
