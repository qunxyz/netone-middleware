/**
 * 
 */
package com.jl.entity;

/**
 * 部门/公司级别树
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2010-11-17 下午02:08:02
 * @history
 */
public class DepartmentLevel {

	/** 分析部门/公司ID */
	private String departmentLevelItemId;
	/** 该级别所在的部门级别情况 */
	private String departmentLevel;
	/** 该级别所在的部门级别 公司/部门ID */
	private String departmentLevelId;
	/** 该级别所在的部门级别 公司/部门编码 */
	private String departmentLevelCode;
	/** 该级别所在的部门级别 公司/部门名称 */
	private String departmentLevelName;
	/** 排序 */
	private int orders;

	public String getDepartmentLevelItemId() {
		return departmentLevelItemId;
	}

	public void setDepartmentLevelItemId(String departmentLevelItemId) {
		this.departmentLevelItemId = departmentLevelItemId;
	}

	public String getDepartmentLevelId() {
		return departmentLevelId;
	}

	public void setDepartmentLevelId(String departmentLevelId) {
		this.departmentLevelId = departmentLevelId;
	}

	public String getDepartmentLevel() {
		return departmentLevel;
	}

	public void setDepartmentLevel(String departmentLevel) {
		this.departmentLevel = departmentLevel;
	}

	public String getDepartmentLevelCode() {
		return departmentLevelCode;
	}

	public void setDepartmentLevelCode(String departmentLevelCode) {
		this.departmentLevelCode = departmentLevelCode;
	}

	public String getDepartmentLevelName() {
		return departmentLevelName;
	}

	public void setDepartmentLevelName(String departmentLevelName) {
		this.departmentLevelName = departmentLevelName;
	}

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}

}
