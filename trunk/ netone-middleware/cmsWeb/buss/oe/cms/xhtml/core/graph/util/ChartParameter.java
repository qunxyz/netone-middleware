package oe.cms.xhtml.core.graph.util;

/**
 * �ṩͼ���һЩ��������
 * 
 * @author hls
 * 
 */
public class ChartParameter {
	
	//ͼ����	 
	private int width;
	
	//ͼ��߶�
	private int heigth;
	
	//ͼ�����
	private String chartTitle ;
	
	//ͼ��X�����
	private String xlable ;
	
	//ͼ��Y�����
	private String ylable ;
	
	//ͼ�����Сֵ
	private String minNum;
	    

	public int getHeigth() {
		return heigth;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getChartTitle() {
		return chartTitle;
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}

	public String getXlable() {
		return xlable;
	}

	public void setXlable(String xlable) {
		this.xlable = xlable;
	}

	public String getYlable() {
		return ylable;
	}

	public void setYlable(String ylable) {
		this.ylable = ylable;
	}

	public String getMinNum() {
		return minNum;
	}

	public void setMinNum(String minNum) {
		this.minNum = minNum;
	}

}
