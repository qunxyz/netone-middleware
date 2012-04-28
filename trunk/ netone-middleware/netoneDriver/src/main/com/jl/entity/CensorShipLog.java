package com.jl.entity;

import java.util.Date;

public class CensorShipLog extends AbstractEntity {
	/** 日志ID */
	private String punid;
	/** 督办ID */
	private String unid;
	/** 操作人 */
	private String sname;
	/** 动作 */
	private String actionname;
	/** 目标人 */
	private String tname;
	/** 操作时间 */
	private Date addtime;
	
	// ext 
	private String yijian;
	private String sdept;

	public String getYijian() {
		return yijian;
	}

	public void setYijian(String yijian) {
		this.yijian = yijian;
	}

	public String getSdept() {
		return sdept;
	}

	public void setSdept(String sdept) {
		this.sdept = sdept;
	}

	public String getPunid() {
		return punid;
	}

	public void setPunid(String punid) {
		this.punid = punid;
	}

	public String getUnid() {
		return unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getActionname() {
		return actionname;
	}

	public void setActionname(String actionname) {
		this.actionname = actionname;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

}
