package oesee.teach.java.oop;


public class Square extends Shape {
	// ���
	double width;

	// �������
	public void area() {
		System.out.println(this.width * this.width);
	}

	public Square(double width) {
		this.width = width;
	}
}
