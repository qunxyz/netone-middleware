package oe.bi.etl.obj;

public class MiddleCode {
	private String datamodelid;

	/**
	 * SQL����Ҫ��õ�ָ���ֶ�
	 */
	private String targetColumnLink;

	private String[] targetColumnNames;

	private String[] targetColumnIds;

	private double[] alarm;

	/** �澯���� */
	private String[] conditionAlarms;

	/** ������Ϣ */
	private String[] targetOrderinfos;

	/**
	 * ͳһ��ͼ
	 */
	private String uniformview;

	/**
	 * Nά�ȵ�SQL����,��ִ��������չ�� ,�ֱ𹹼���SQLȥִ��
	 */
	private String[] dimensionConditions;

	private String[] dimensionColumnValues;

	private String[] dimensionColumnValueName;

	private String[] dimensionColumnNames;

	private String[] dimensionColumnIds;

	private String[] dimensionTypes;

	private String[] dimensionlevel;

	/** ������Ϣ */
	private String[] dimensionOrderinfos;

	public String[] getConditionAlarms() {
		return conditionAlarms;
	}

	public void setConditionAlarms(String[] conditionAlarms) {
		this.conditionAlarms = conditionAlarms;
	}

	public String[] getDimensionColumnIds() {
		return dimensionColumnIds;
	}

	public void setDimensionColumnIds(String[] dimensionColumnIds) {
		this.dimensionColumnIds = dimensionColumnIds;
	}

	public String[] getDimensionColumnNames() {
		return dimensionColumnNames;
	}

	public void setDimensionColumnNames(String[] dimensionColumnNames) {
		this.dimensionColumnNames = dimensionColumnNames;
	}

	public String[] getDimensionColumnValues() {
		return dimensionColumnValues;
	}

	public void setDimensionColumnValues(String[] dimensionColumnValues) {
		this.dimensionColumnValues = dimensionColumnValues;
	}

	public String[] getDimensionConditions() {
		return dimensionConditions;
	}

	public void setDimensionConditions(String[] dimensionConditions) {
		this.dimensionConditions = dimensionConditions;
	}

	public String[] getDimensionOrderinfos() {
		return dimensionOrderinfos;
	}

	public void setDimensionOrderinfos(String[] dimensionOrderinfos) {
		this.dimensionOrderinfos = dimensionOrderinfos;
	}

	public String[] getTargetColumnIds() {
		return targetColumnIds;
	}

	public void setTargetColumnIds(String[] targetColumnIds) {
		this.targetColumnIds = targetColumnIds;
	}

	public String getTargetColumnLink() {
		return targetColumnLink;
	}

	public void setTargetColumnLink(String targetColumnLink) {
		this.targetColumnLink = targetColumnLink;
	}

	public String[] getTargetColumnNames() {
		return targetColumnNames;
	}

	public void setTargetColumnNames(String[] targetColumnNames) {
		this.targetColumnNames = targetColumnNames;
	}

	public String[] getTargetOrderinfos() {
		return targetOrderinfos;
	}

	public void setTargetOrderinfos(String[] targetOrderinfos) {
		this.targetOrderinfos = targetOrderinfos;
	}

	public String getUniformview() {
		return uniformview;
	}

	public void setUniformview(String uniformview) {
		this.uniformview = uniformview;
	}

	public double[] getAlarm() {
		return alarm;
	}

	public void setAlarm(double[] alarm) {
		this.alarm = alarm;
	}

	public String[] getDimensionTypes() {
		return dimensionTypes;
	}

	public void setDimensionTypes(String[] dimensionTypes) {
		this.dimensionTypes = dimensionTypes;
	}

	public String[] getDimensionlevel() {
		return dimensionlevel;
	}

	public void setDimensionlevel(String[] dimensionlevel) {
		this.dimensionlevel = dimensionlevel;
	}

	public String[] getDimensionColumnValueName() {
		return dimensionColumnValueName;
	}

	public void setDimensionColumnValueName(String[] dimensionColumnValueName) {
		this.dimensionColumnValueName = dimensionColumnValueName;
	}

	public String getDatamodelid() {
		return datamodelid;
	}

	public void setDatamodelid(String datamodelid) {
		this.datamodelid = datamodelid;
	}

}
