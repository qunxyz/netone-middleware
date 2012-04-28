package com.jl.entity;

import java.util.Date;

import oe.serialize.dao.IdEntity;

/**
 * oe.fjnusoft.exam.entity.AbstractEntity.java
 * <p>
 * <li>抽象实体类</li>
 * </p>
 * <description></description>
 * 
 * @author zhang.chao.yi
 * @version 1.0
 * @date 2009-3-16
 * @histroy 1.0 date 2009-3-16 modified by zhang.chao.yi
 */
public abstract class AbstractEntity extends IdEntity {

	/** 备注 */
	private String note;
	/** 操作员 */
	private String operate;
	/** 操作时间 */
	private Date operateTime;
	/** 状态名称 */
	private String statusName;

	/** 组织机构 */
	private String departmentId1;
	private String departmentCode1;
	private String departmentName1;
	private String departmentId2;
	private String departmentCode2;
	private String departmentName2;
	private String departmentId3;
	private String departmentCode3;
	private String departmentName3;
	private String departmentId4;
	private String departmentCode4;
	private String departmentName4;
	private String departmentId5;
	private String departmentCode5;
	private String departmentName5;
	private String departmentIdx;
	private String departmentCodex;
	private String departmentNamex;
	private String departmentIds1;
	private String departmentCodes1;
	private String departmentNames1;
	private String departmentIds2;
	private String departmentCodes2;
	private String departmentNames2;

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the operate
	 */
	public String getOperate() {
		return operate;
	}

	/**
	 * @param operate
	 *            the operate to set
	 */
	public void setOperate(String operate) {
		this.operate = operate;
	}

	/**
	 * @return the operateTime
	 */
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * @param operateTime
	 *            the operateTime to set
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getDepartmentId1() {
		return departmentId1;
	}

	public void setDepartmentId1(String departmentId1) {
		this.departmentId1 = departmentId1;
	}

	public String getDepartmentCode1() {
		return departmentCode1;
	}

	public void setDepartmentCode1(String departmentCode1) {
		this.departmentCode1 = departmentCode1;
	}

	public String getDepartmentName1() {
		return departmentName1;
	}

	public void setDepartmentName1(String departmentName1) {
		this.departmentName1 = departmentName1;
	}

	public String getDepartmentId2() {
		return departmentId2;
	}

	public void setDepartmentId2(String departmentId2) {
		this.departmentId2 = departmentId2;
	}

	public String getDepartmentCode2() {
		return departmentCode2;
	}

	public void setDepartmentCode2(String departmentCode2) {
		this.departmentCode2 = departmentCode2;
	}

	public String getDepartmentName2() {
		return departmentName2;
	}

	public void setDepartmentName2(String departmentName2) {
		this.departmentName2 = departmentName2;
	}

	public String getDepartmentId3() {
		return departmentId3;
	}

	public void setDepartmentId3(String departmentId3) {
		this.departmentId3 = departmentId3;
	}

	public String getDepartmentCode3() {
		return departmentCode3;
	}

	public void setDepartmentCode3(String departmentCode3) {
		this.departmentCode3 = departmentCode3;
	}

	public String getDepartmentName3() {
		return departmentName3;
	}

	public void setDepartmentName3(String departmentName3) {
		this.departmentName3 = departmentName3;
	}

	public String getDepartmentId4() {
		return departmentId4;
	}

	public void setDepartmentId4(String departmentId4) {
		this.departmentId4 = departmentId4;
	}

	public String getDepartmentCode4() {
		return departmentCode4;
	}

	public void setDepartmentCode4(String departmentCode4) {
		this.departmentCode4 = departmentCode4;
	}

	public String getDepartmentName4() {
		return departmentName4;
	}

	public void setDepartmentName4(String departmentName4) {
		this.departmentName4 = departmentName4;
	}

	public String getDepartmentId5() {
		return departmentId5;
	}

	public void setDepartmentId5(String departmentId5) {
		this.departmentId5 = departmentId5;
	}

	public String getDepartmentCode5() {
		return departmentCode5;
	}

	public void setDepartmentCode5(String departmentCode5) {
		this.departmentCode5 = departmentCode5;
	}

	public String getDepartmentName5() {
		return departmentName5;
	}

	public void setDepartmentName5(String departmentName5) {
		this.departmentName5 = departmentName5;
	}

	public String getDepartmentIdx() {
		return departmentIdx;
	}

	public void setDepartmentIdx(String departmentIdx) {
		this.departmentIdx = departmentIdx;
	}

	public String getDepartmentCodex() {
		return departmentCodex;
	}

	public void setDepartmentCodex(String departmentCodex) {
		this.departmentCodex = departmentCodex;
	}

	public String getDepartmentNamex() {
		return departmentNamex;
	}

	public void setDepartmentNamex(String departmentNamex) {
		this.departmentNamex = departmentNamex;
	}

	public String getDepartmentIds1() {
		return departmentIds1;
	}

	public void setDepartmentIds1(String departmentIds1) {
		this.departmentIds1 = departmentIds1;
	}

	public String getDepartmentCodes1() {
		return departmentCodes1;
	}

	public void setDepartmentCodes1(String departmentCodes1) {
		this.departmentCodes1 = departmentCodes1;
	}

	public String getDepartmentNames1() {
		return departmentNames1;
	}

	public void setDepartmentNames1(String departmentNames1) {
		this.departmentNames1 = departmentNames1;
	}

	public String getDepartmentIds2() {
		return departmentIds2;
	}

	public void setDepartmentIds2(String departmentIds2) {
		this.departmentIds2 = departmentIds2;
	}

	public String getDepartmentCodes2() {
		return departmentCodes2;
	}

	public void setDepartmentCodes2(String departmentCodes2) {
		this.departmentCodes2 = departmentCodes2;
	}

	public String getDepartmentNames2() {
		return departmentNames2;
	}

	public void setDepartmentNames2(String departmentNames2) {
		this.departmentNames2 = departmentNames2;
	}

}
