package oesee.teach.java.oop.sample4;

public class RoundImpl implements Shape {
	double r;

	public RoundImpl(double r) {
		this.r = r;
	}

	public void area() {
		System.out.println(r * r * 3.14);
	}
	
	public void area1(){
		
	}

}
