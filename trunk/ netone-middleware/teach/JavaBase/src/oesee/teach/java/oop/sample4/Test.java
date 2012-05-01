package oesee.teach.java.oop.sample4;

public class Test {

	public static void main(String[] args) {
		RoundImpl round = new RoundImpl(10);
		SquareImpl square = new SquareImpl();


		useArea(round);
		useArea(square);
		
		
		int abc=0;
		long efg=abc;
		
		long abc1=0;
	
		
		Shape abcx =new RoundImpl(10);
		Shape efgx=new SquareImpl();
		
	}

	public static void useArea(Shape papm) {
		papm.area();
	}


}
