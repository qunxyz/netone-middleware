package oesee.teach.java.one.mycookies;

/**
 * ������
 * 
 * @author chen.jia.xun(Robanco) www.oesee.com
 * 
 */
public class Rectangle {

	// �߶�
	double height;

	// ���
	double width;

	public Rectangle(double height, double width) {
		this.height = height;
		this.width = width;
	}

	/**
	 * �������
	 * 
	 */
	void area() {
		System.out.println(this.height * this.width);
	}

}
