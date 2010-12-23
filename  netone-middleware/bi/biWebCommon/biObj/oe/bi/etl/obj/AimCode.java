package oe.bi.etl.obj;

/**
 * 根据ChoiceInfo中选择的信息,结合业务模型,所生成的最终可用于装载分析数据的目标代码
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class AimCode {

	String datamodelid;

	/** 结果集的指标名 */
	private String[] targetColumnnames;

	/** 结果集的指标字段 */
	private String[] targetColumnIds;

	/** 结果集的报警信息 */
	private double[] alarms;

	/**
	 * 需要执行的SQL
	 */
	private String[] sql;

	/**
	 * 需要在结果集中加入的维度信息
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
