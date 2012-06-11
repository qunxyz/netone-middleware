/**
 * 
 */
package com.report.flashchart.entity;

/**
 * 图表数据对象
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-1-30 下午04:03:14
 * @history
 */
public class ChartDataObj implements java.io.Serializable {

	/**
	 * 标题
	 */
	private String title;

	/**
	 * X轴label
	 */
	private String xAxisLabel;

	/**
	 * Y轴label
	 */
	private String yAxisLabel;

	/**
	 * 以JSON格式构造 例:{title:'标题'}
	 */
	private String params;

	private String endpoint;

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getXAxisLabel() {
		return xAxisLabel;
	}

	public void setXAxisLabel(String axisLabel) {
		xAxisLabel = axisLabel;
	}

	public String getYAxisLabel() {
		return yAxisLabel;
	}

	public void setYAxisLabel(String axisLabel) {
		yAxisLabel = axisLabel;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

}
