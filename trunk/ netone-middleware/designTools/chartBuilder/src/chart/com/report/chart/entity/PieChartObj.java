package com.report.chart.entity;

/**
 * ��ͼ����
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-11-22 ����02:15:10
 * @history
 */
public class PieChartObj extends BaseChartObj {

	/**
	 * ��ֵ
	 */
	public String key;
	/**
	 * ֵ
	 */
	public Double value;

	/**
	 * ���ݲ�������,��ʽ��JSON�洢���� ��:{"p":1,"p2":"IDֵ","title":"����"}<BR>
	 * ע:titleΪ�̶���������ʹ��,���ֶ�������ʾ��ȡ���ҳ��ı���,��û��title�ֶ���ʹ��key��Ϊ����
	 */
	public String tips;

	/**
	 * ���ݲ�������,��ʽ��JSON�洢���� ��:{"p":1,"p2":"IDֵ"}
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
