package com.jl.entity;

import java.util.Date;

public class CensorShip extends AbstractEntity {

	/** 督办ID */
	private String unid;
	/** 督办名称 */
	private String subject;
	/** 督办发起人 */
	private String handler;
	/** 督办发起部门 */
	private String chargedept;
	/** 要求完成时间 */
	private Date duetime;
	/** 实际完成时间 */
	private Date donetime;
	/** 参与部门 */
	private String transdept;
	/** 督办说明 */
	private String memo;
	/** 督办发起时间 */
	private Date newtime;
	/** 督办来源 */
	private String frome;
	
	//ext
	private boolean iscreater;
	private String handlerid;

	public String getHandlerid() {
		return handlerid;
	}

	public void setHandlerid(String handlerid) {
		this.handlerid = handlerid;
	}

	public String getUnid() {
		return unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getChargedept() {
		return chargedept;
	}

	public void setChargedept(String chargedept) {
		this.chargedept = chargedept;
	}

	public Date getDuetime() {
		return duetime;
	}

	public void setDuetime(Date duetime) {
		this.duetime = duetime;
	}

	public Date getDonetime() {
		return donetime;
	}

	public void setDonetime(Date donetime) {
		this.donetime = donetime;
	}

	public String getTransdept() {
		return transdept;
	}

	public void setTransdept(String transdept) {
		this.transdept = transdept;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getNewtime() {
		return newtime;
	}

	public void setNewtime(Date newtime) {
		this.newtime = newtime;
	}

	public String getFrome() {
		return frome;
	}

	public void setFrome(String frome) {
		this.frome = frome;
	}

	public boolean isIscreater() {
		return iscreater;
	}

	public void setIscreater(boolean iscreater) {
		this.iscreater = iscreater;
	}


}
