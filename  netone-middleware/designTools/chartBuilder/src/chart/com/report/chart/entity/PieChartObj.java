package com.report.chart.entity;

/**
 * 饼图对象
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-11-22 下午02:15:10
 * @history
 */
public class PieChartObj extends BaseChartObj {

	/**
	 * 键值
	 */
	public String key;
	/**
	 * 值
	 */
	public Double value;

	/**
	 * 传递参数变量,格式以JSON存储起来 例:{"p":1,"p2":"ID值","title":"标题"}<BR>
	 * 注:title为固定变量不能使用,该字段用于显示钻取或打开页面的标题,若没有title字段则使用key作为标题
	 */
	public String tips;

	/**
	 * 传递参数变量,格式以JSON存储起来 例:{"p":1,"p2":"ID值"}
	 */
	public String params;

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

}
