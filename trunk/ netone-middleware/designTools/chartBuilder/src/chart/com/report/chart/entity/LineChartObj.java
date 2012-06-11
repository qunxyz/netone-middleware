package com.report.chart.entity;

/**
 * ���ζ���
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-11-22 ����02:15:10
 * @history
 */
public class LineChartObj extends BaseChartObj {

	/**
	 * ֵ
	 */
	public Double value;
	/**
	 * �м�ֵ
	 */
	public String rowKey;
	/**
	 * �м�ֵ
	 */
	public String columnKey;

	/**
	 * ��ʾЧ������(ע��flashͼ��ʹ��,������ʽ��Ч)
	 */
	public String tips;

	/**
	 * ���ݲ�������,��ʽ��JSON�洢���� ��:{"p":1,"p2":"IDֵ","title":"����"}<BR>
	 * ע:titleΪ�̶���������ʹ��,���ֶ�������ʾ��ȡ���ҳ��ı���,��û��title�ֶ���ʹ��key��Ϊ����
	 */
	public String params;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

}
