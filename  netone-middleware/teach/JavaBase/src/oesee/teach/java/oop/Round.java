package oesee.teach.java.oop;


public class Round extends Shape {
	// 宽度
	double r;

	// 计算面积
	public void area(double pi) {
		System.out.println(pi * r * r);
	}

	public Round(double r) {
		this.r = r;
	}
}
