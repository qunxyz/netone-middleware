package oesee.teach.java.oop;


public class Round extends Shape {
	// ���
	double r;

	// �������
	public void area(double pi) {
		System.out.println(pi * r * r);
	}

	public Round(double r) {
		this.r = r;
	}
}
