/**
 * 
 */
package com.jl.entity;

/**
 * 经销商级别划分表
 * 
 * @author Don (cai.you.dun)
 * @version 1.0.0 date Jan 5, 2010 create by Don
 * @history
 */
public class ClientPriceLevel {

	/** 级别流水号 */
	private String levelCode;

	/** 级别名称 */
	private String levelName;

	/** 父级别流水号 */
	private String parentLevelCode;

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getParentLevelCode() {
		return parentLevelCode;
	}

	public void setParentLevelCode(String parentLevelCode) {
		this.parentLevelCode = parentLevelCode;
	}
}
