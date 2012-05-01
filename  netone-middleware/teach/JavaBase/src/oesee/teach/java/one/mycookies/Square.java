package oesee.teach.java.one.mycookies;

/**
 * 正方形
 * 
 * @author chen.jia.xun(Robanco) www.oesee.com
 * 
 */
public class Square {

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
