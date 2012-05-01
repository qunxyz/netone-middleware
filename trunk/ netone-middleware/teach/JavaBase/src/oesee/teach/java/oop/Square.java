package oesee.teach.java.oop;


public class Square extends Shape {
	// 宽度
	double width;

	// 计算面积
	public void area() {
		System.out.println(this.width * this.width);
	}

	public Square(double width) {
		this.width = width;
	}
}
