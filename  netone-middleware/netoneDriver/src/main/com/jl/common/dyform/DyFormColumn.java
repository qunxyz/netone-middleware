package com.jl.common.dyform;

import oe.cav.bean.logic.column.TCsColumn;

public final class DyFormColumn extends TCsColumn {

	int groupsize = 1;

	boolean readonly;

	boolean musk_;

	boolean hidden;

	double xoffset;

	double yoffset;

	/** 宽度百分比 */
	double wpercent;

	String initScript;

	String focusScript;

	String loseFocusScript;

	String onchangeScript;
	/**
	 * 正则表达式
	 */
	String regExpression;

	double width;

	/**
	 * 默认值
	 */
	String defaultValue;

	/** 汇总类型 sum,average,count,max,min */
	String summarytype;

	public String getSummarytype() {
		return summarytype;
	}

	public void setSummarytype(String summarytype) {
		this.summarytype = summarytype;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public boolean isMusk_() {
		return musk_;
	}

	public void setMusk_(boolean musk_) {
		this.musk_ = musk_;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public double getXoffset() {
		return xoffset;
	}

	public void setXoffset(double xoffset) {
		this.xoffset = xoffset;
	}

	public double getYoffset() {
		return yoffset;
	}

	public void setYoffset(double yoffset) {
		this.yoffset = yoffset;
	}

	public String getInitScript() {
		return initScript;
	}

	public void setInitScript(String initScript) {
		this.initScript = initScript;
	}

	public String getFocusScript() {
		return focusScript;
	}

	public void setFocusScript(String focusScript) {
		this.focusScript = focusScript;
	}

	public String getLoseFocusScript() {
		return loseFocusScript;
	}

	public void setLoseFocusScript(String loseFocusScript) {
		this.loseFocusScript = loseFocusScript;
	}

	public double getWpercent() {
		return wpercent;
	}

	public void setWpercent(double wpercent) {
		this.wpercent = wpercent;
	}

	public String getRegExpression() {
		return regExpression;
	}

	public void setRegExpression(String regExpression) {
		this.regExpression = regExpression;
	}

	public String getOnchangeScript() {
		return onchangeScript;
	}

	public void setOnchangeScript(String onchangeScript) {
		this.onchangeScript = onchangeScript;
	}

	public int getGroupsize() {
		return groupsize;
	}

	public void setGroupsize(int groupsize) {
		this.groupsize = groupsize;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}
