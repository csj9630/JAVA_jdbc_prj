package kr.co.sist.statement.dto;

import java.sql.Date;

public class MemberDTO {
	private int num,age;				
	private String name,gender,tel;
	private Date input_date;
	
	public MemberDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MemberDTO(int num,String name, int age, String gender, String tel, Date input_date) {
		super();
		this.num = num;
		this.age = age;
		this.name = name;
		this.gender = gender;
		this.tel = tel;
		this.input_date = input_date;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	@Override
	public String toString() {
		return "MemberDTO [num=" + num + ", age=" + age + ", name=" + name + ", gender=" + gender + ", tel=" + tel
				+ ", input_date=" + input_date + "]";
	}
	public Date getInput_date() {
		return input_date;
	}
	public void setInput_date(Date input_date) {
		this.input_date = input_date;
	}

}//class


