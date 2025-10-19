package day1013;

public class UseSingleton {

	public static void main(String[] args) {
		//생성자의 접근지정자를 private으로 하면 클래스외부에서 객체화할 수 없다.
//		Singleton s = new Singleton();
//		Singleton s2 = new Singleton();
		
		//객체를 반환하는 일을 하는 method를 호출하여 클래스외부에서 객체를 얻는다.
		Singleton s=Singleton.getInstance();
		Singleton s2=Singleton.getInstance();

		
		
	}//main

}//class