package oe.bi.common.chart;

/**
 * 提供图表的一些基本参数
 * 
 * @author hls
 * 
 */
public class ChartParameter {

	private int width;

	private int heigth;
	
	private String chartTitle ;
	
	private String xlable ;
	
	private String ylable ;
	
	private int maxvalue ;
	
	private boolean showvalue;
	
	private String piccolor;
	
	private String xqingxie;
	

	public int getMaxvalue() {
		return maxvalue;
	}

	public void setMaxvalue(int maxvalue) {
		this.maxvalue = maxvalue;
	}

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

	public boolean isShowvalue() {
		return showvalue;
	}

	public void setShowvalue(boolean showvalue) {
		this.showvalue = showvalue;
	}

	public String getPiccolor() {
		return piccolor;
	}

	public void setPiccolor(String piccolor) {
		this.piccolor = piccolor;
	}

	public String getXqingxie() {
		return xqingxie;
	}

	public void setXqingxie(String xqingxie) {
		this.xqingxie = xqingxie;
	}

}
