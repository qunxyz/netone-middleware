package oe.bi.view.obj;

import java.io.Serializable;
import java.util.Map;

/**
 * ��ͼ���ݲ���
 * 
 * @author chen.jia.xun (Robanco) ע�⣺ ��Ա����
 *         targetValue��dimValue��dimName��targetName �ĳ�������ͬ��
 * 
 */

public class ViewModel implements Serializable {

	/** ά�� */
	private String[][] dimensionvalue;

	private String[][] dimensionvalueName;

	/** ά������ */
	private String[] dimensionname;

	/** ά��ID */
	private String[] dimensionid;

	private String[] dimensionlevel;

	/**
	 * ά������
	 */
	private String[] dimensionType;

	/** ָ�� */
	private double[][] targetvalue;

	/** ָ������ */
	private String[] targetname;

	/** ָ��ID */
	private String[] targetid;

	/** ָ��澯 */
	private double[] targetalarm;

	/** ��չ���� */
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
