/**
 * 
 */
package com.jl.entity;

/**
 * �����̼��𻮷ֱ�
 * 
 * @author Don (cai.you.dun)
 * @version 1.0.0 date Jan 5, 2010 create by Don
 * @history
 */
public class ClientPriceLevel {

	/** ������ˮ�� */
	private String levelCode;

	/** �������� */
	private String levelName;

	/** ��������ˮ�� */
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
