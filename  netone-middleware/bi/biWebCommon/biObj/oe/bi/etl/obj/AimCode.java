package oe.bi.etl.obj;

/**
 * ����ChoiceInfo��ѡ�����Ϣ,���ҵ��ģ��,�����ɵ����տ�����װ�ط������ݵ�Ŀ�����
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class AimCode {

	String datamodelid;

	/** �������ָ���� */
	private String[] targetColumnnames;

	/** �������ָ���ֶ� */
	private String[] targetColumnIds;

	/** ������ı�����Ϣ */
	private double[] alarms;

	/**
	 * ��Ҫִ�е�SQL
	 */
	private String[] sql;

	/**
	 * ��Ҫ�ڽ�����м����ά����Ϣ
	 */
	private String[] dimensionvalues;

	private String[] dimensionColumnValueName;

	private String[] dimensionNames;

	private String[] dimensionIds;

	private String[] dimensionTypes;

	private String[] dimensionlevel;

	public String[] getDimensionlevel() {
		return dimensionlevel;
	}

	public void setDimensionlevel(String[] dimensionlevel) {
		this.dimensionlevel = dimensionlevel;
	}

	public String[] getDimensionTypes() {
		return dimensionTypes;
	}

	public void setDimensionTypes(String[] dimensionTypes) {
		this.dimensionTypes = dimensionTypes;
	}

	public double[] getAlarms() {
		return alarms;
	}

	public void setAlarms(double[] alarms) {
		this.alarms = alarms;
	}

	public String[] getDimensionIds() {
		return dimensionIds;
	}

	public void setDimensionIds(String[] dimensionIds) {
		this.dimensionIds = dimensionIds;
	}

	public String[] getDimensionNames() {
		return dimensionNames;
	}

	public void setDimensionNames(String[] dimensionNames) {
		this.dimensionNames = dimensionNames;
	}

	public String[] getDimensionvalues() {
		return dimensionvalues;
	}

	public void setDimensionvalues(String[] dimensionvalues) {
		this.dimensionvalues = dimensionvalues;
	}

	public String[] getSql() {
		return sql;
	}

	public void setSql(String[] sql) {
		this.sql = sql;
	}

	public String[] getTargetColumnIds() {
		return targetColumnIds;
	}

	public void setTargetColumnIds(String[] targetColumnIds) {
		this.targetColumnIds = targetColumnIds;
	}

	public String[] getTargetColumnnames() {
		return targetColumnnames;
	}

	public void setTargetColumnnames(String[] targetColumnnames) {
		this.targetColumnnames = targetColumnnames;
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
