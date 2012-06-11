/**
 * 
 */
package com.report.flashchart.entity;

/**
 * ͼ�����ݶ���
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-1-30 ����04:03:14
 * @history
 */
public class ChartDataObj implements java.io.Serializable {

	/**
	 * ����
	 */
	private String title;

	/**
	 * X��label
	 */
	private String xAxisLabel;

	/**
	 * Y��label
	 */
	private String yAxisLabel;

	/**
	 * ��JSON��ʽ���� ��:{title:'����'}
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
