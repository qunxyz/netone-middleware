package com.lucaslee.report.printer;

/**
 * ��x,y�����ʾ��һ���㡣
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author Lucas lee
 * @version 1.0
 */
public class Point {
	private double x = 0.0;

	private double y = 0.0;

	/**
	 * 
	 * @param x
	 *            x����
	 * @param y
	 *            y����
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * ���x����
	 * 
	 * @return
	 */
	public double getX() {
		return x;
	}

	/**
	 * ���y����
	 * 
	 * @return
	 */
	public double getY() {
		return y;
	}

	/**
	 * ����y����
	 * 
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * ����x����
	 * 
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}

}
