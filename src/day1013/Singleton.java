package day1013;
/**
 * 실행 중인 JVM에서 객체를 하나로 만들고 사용할 목적의 클래스.
 */
public class Singleton {
	private static Singleton single;


	/**
	 * 생성자의 접근 지정자를 private으로 설정.
	 */
	private Singleton() {
		super();
	}//Singleton
	
	public static Singleton getInstance() {
		if(single==null) {//객체가 생성되어 있지 않을 때만
			single = new Singleton();//새로 객체를 생성하라.
			
		}//end if
		return single;
	}//getInstance

}//class
