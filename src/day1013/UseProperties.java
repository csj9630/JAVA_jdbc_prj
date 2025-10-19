package day1013;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class UseProperties {

	public static void main(String[] args) {
		
		//1.생성
		 Properties prop = new Properties();
		 
		 //2.Properties 파일 연결
		 try {
			 //properties 파일 경로를 넣는다.
			prop.load(new FileInputStream("C:\\dev\\workspace\\jdbc_prj\\src\\properties\\database.properties"));
			
			//3.값 얻기
			String url = prop.getProperty("url");
			String id = prop.getProperty("id");
			String pass = prop.getProperty("pass");
			
			System.out.println(url);
			System.out.println(id);
			System.out.println(pass);
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}//catch

	}//main

}//class