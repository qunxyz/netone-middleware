/**
 * 
 */
package com.jl.entity;

/**
 * ����/��˾������
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2010-11-17 ����02:08:02
 * @history
 */
public class DepartmentLevel {

	/** ��������/��˾ID */
	private String departmentLevelItemId;
	/** �ü������ڵĲ��ż������ */
	private String departmentLevel;
	/** �ü������ڵĲ��ż��� ��˾/����ID */
	private String departmentLevelId;
	/** �ü������ڵĲ��ż��� ��˾/���ű��� */
	private String departmentLevelCode;
	/** �ü������ڵĲ��ż��� ��˾/�������� */
	private String departmentLevelName;
	/** ���� */
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
