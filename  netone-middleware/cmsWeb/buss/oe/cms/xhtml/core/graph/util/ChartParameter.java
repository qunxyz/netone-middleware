package oe.cms.xhtml.core.graph.util;

/**
 * 提供图表的一些基本参数
 * 
 * @author hls
 * 
 */
public class ChartParameter {
	
	//图表宽度	 
	private int width;
	
	//图表高度
	private int heigth;
	
	//图表标题
	private String chartTitle ;
	
	//图表X轴标题
	private String xlable ;
	
	//图表Y轴标题
	private String ylable ;
	
	//图表的最小值
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
