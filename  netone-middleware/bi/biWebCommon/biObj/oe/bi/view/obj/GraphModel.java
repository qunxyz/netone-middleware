package oe.bi.view.obj;

import java.io.Serializable;
import java.util.Map;

/**
 * 图表对象 也就是切片模型
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class GraphModel implements Serializable {
	/**
	 * X坐标信息，必须确定的
	 */
	private String xOffsetDimension;

	private Map otherDimension;

	private String choicetarget;// 逗号串着N个选择的指标

	private String graphType;

	private String xoffsetDimensionForcastValue;// 逗号串着N个选择的预测维度值

	private String xoffsetDimensionForcastValueNext;// 逗号串着N个选择的预测维度值

	private String forcastArithmetic;

	private String forcastLevelid;// 预测点所在的水平位置

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
