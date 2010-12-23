package oe.bi.view.obj;

import java.io.Serializable;
import java.util.Map;

/**
 * 视图数据参数
 * 
 * @author chen.jia.xun (Robanco) 注意： 成员属性
 *         targetValue，dimValue，dimName，targetName 的长度是相同的
 * 
 */

public class ViewModel implements Serializable {

	/** 维度 */
	private String[][] dimensionvalue;

	private String[][] dimensionvalueName;

	/** 维度名称 */
	private String[] dimensionname;

	/** 维度ID */
	private String[] dimensionid;

	private String[] dimensionlevel;

	/**
	 * 维度类型
	 */
	private String[] dimensionType;

	/** 指标 */
	private double[][] targetvalue;

	/** 指标名称 */
	private String[] targetname;

	/** 指标ID */
	private String[] targetid;

	/** 指标告警 */
	private double[] targetalarm;

	/** 扩展属性 */
	private Map otherDim;

	private String extendAttribute;

	public String[] getDimensionid() {
		return dimensionid;
	}

	public void setDimensionid(String[] dimensionid) {
		this.dimensionid = dimensionid;
	}

	public String[] getDimensionname() {
		return dimensionname;
	}

	public void setDimensionname(String[] dimensionname) {
		this.dimensionname = dimensionname;
	}

	public String[][] getDimensionvalue() {
		return dimensionvalue;
	}

	public void setDimensionvalue(String[][] dimensionvalue) {
		this.dimensionvalue = dimensionvalue;
	}

	public double[] getTargetalarm() {
		return targetalarm;
	}

	public void setTargetalarm(double[] targetalarm) {
		this.targetalarm = targetalarm;
	}

	public String[] getTargetid() {
		return targetid;
	}

	public void setTargetid(String[] targetid) {
		this.targetid = targetid;
	}

	public String[] getTargetname() {
		return targetname;
	}

	public void setTargetname(String[] targetname) {
		this.targetname = targetname;
	}

	public double[][] getTargetvalue() {
		return targetvalue;
	}

	public void setTargetvalue(double[][] targetvalue) {
		this.targetvalue = targetvalue;
	}

	public String[] getDimensionType() {
		return dimensionType;
	}

	public void setDimensionType(String[] dimensionType) {
		this.dimensionType = dimensionType;
	}

	public String[] getDimensionlevel() {
		return dimensionlevel;
	}

	public void setDimensionlevel(String[] dimensionlevel) {
		this.dimensionlevel = dimensionlevel;
	}

	public String[][] getDimensionvalueName() {
		return dimensionvalueName;
	}

	public void setDimensionvalueName(String[][] dimensionvalueName) {
		this.dimensionvalueName = dimensionvalueName;
	}

	public String getExtendAttribute() {
		return extendAttribute;
	}

	public void setExtendAttribute(String extendAttribute) {
		this.extendAttribute = extendAttribute;
	}

	public Map getOtherDim() {
		return otherDim;
	}

	public void setOtherDim(Map otherDim) {
		this.otherDim = otherDim;
	}

}
