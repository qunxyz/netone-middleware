package oe.bi.view.obj;

import java.io.Serializable;
import java.util.Map;

/**
 * ͼ����� Ҳ������Ƭģ��
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class GraphModel implements Serializable {
	/**
	 * X������Ϣ������ȷ����
	 */
	private String xOffsetDimension;

	private Map otherDimension;

	private String choicetarget;// ���Ŵ���N��ѡ���ָ��

	private String graphType;

	private String xoffsetDimensionForcastValue;// ���Ŵ���N��ѡ���Ԥ��ά��ֵ

	private String xoffsetDimensionForcastValueNext;// ���Ŵ���N��ѡ���Ԥ��ά��ֵ

	private String forcastArithmetic;

	private String forcastLevelid;// Ԥ������ڵ�ˮƽλ��

	public String getForcastLevelid() {
		return forcastLevelid;
	}

	public void setForcastLevelid(String forcastLevelid) {
		this.forcastLevelid = forcastLevelid;
	}

	public String getForcastArithmetic() {
		return forcastArithmetic;
	}

	public void setForcastArithmetic(String forcastArithmetic) {
		this.forcastArithmetic = forcastArithmetic;
	}

	public String getXoffsetDimensionForcastValue() {
		return xoffsetDimensionForcastValue;
	}

	public void setXoffsetDimensionForcastValue(
			String xoffsetDimensionForcastValue) {
		this.xoffsetDimensionForcastValue = xoffsetDimensionForcastValue;
	}

	public String getGraphType() {
		return graphType;
	}

	public void setGraphType(String graphType) {
		this.graphType = graphType;
	}

	public String getXOffsetDimension() {
		return xOffsetDimension;
	}

	public void setXOffsetDimension(String offsetDimension) {
		xOffsetDimension = offsetDimension;
	}

	public String getChoicetarget() {
		return choicetarget;
	}

	public void setChoicetarget(String choicetarget) {
		this.choicetarget = choicetarget;
	}

	public Map getOtherDimension() {
		return otherDimension;
	}

	public void setOtherDimension(Map otherDimension) {
		this.otherDimension = otherDimension;
	}

	public String getXoffsetDimensionForcastValueNext() {
		return xoffsetDimensionForcastValueNext;
	}

	public void setXoffsetDimensionForcastValueNext(
			String xoffsetDimensionForcastValueNext) {
		this.xoffsetDimensionForcastValueNext = xoffsetDimensionForcastValueNext;
	}

}
