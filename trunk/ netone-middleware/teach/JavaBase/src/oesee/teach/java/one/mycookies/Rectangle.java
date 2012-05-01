package oesee.teach.java.one.mycookies;

/**
 * 方形类
 * 
 * @author chen.jia.xun(Robanco) www.oesee.com
 * 
 */
public class Rectangle {

	// 高度
	double height;

	// 宽度
	double width;

	public Rectangle(double height, double width) {
		this.height = height;
		this.width = width;
	}

	/**
	 * 计算面积
	 * 
	 */
	void area() {
		System.out.println(this.height * this.width);
	}

}
