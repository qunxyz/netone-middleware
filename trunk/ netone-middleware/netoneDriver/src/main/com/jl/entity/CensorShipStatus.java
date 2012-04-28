package com.jl.entity;

import java.util.Date;

public class CensorShipStatus extends AbstractEntity {

	/** 督办ID */
	private String unid;
	/** 人员ID */
	private String perunid;
	/** 父人员ID */
	private String parentunid;
	/** 意见 */
	private String yijian;
	/** 状态 */
	private Integer state;
	/** 填写时间 */
	private Date addtime;
	/** 是否删除 */
	private Integer isdelete;
	/** 日志ID */
	private String loglinkunid;
	
	public String getUnid() {
		return unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}

	public String getPerunid() {
		return perunid;
	}

	public void setPerunid(String perunid) {
		this.perunid = perunid;
	}

	public String getParentunid() {
		return parentunid;
	}

	public void setParentunid(String parentunid) {
		this.parentunid = parentunid;
	}

	public String getYijian() {
		return yijian;
	}

	public void setYijian(String yijian) {
		this.yijian = yijian;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}

	public String getLoglinkunid() {
		return loglinkunid;
	}

	public void setLoglinkunid(String loglinkunid) {
		this.loglinkunid = loglinkunid;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

}
