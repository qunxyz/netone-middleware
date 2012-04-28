package com.jl.common.workflow;

import java.io.Serializable;
import java.util.List;

/**
 * 代办任务
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:oesee@139.com<br>
 * 
 */
public final class TWfWorklistExt implements Serializable {
	String appid;
	// 业务id
	String bussid;
	// 业务地址，通过revBussUrl+revBussId 组成一个完整的业务表单的访问地址
	String bussurl;
	// 该任务的启动时间
	String starttime;
	// 活动Code
	String workcode;
	// 业务提示
	String busstip;
	// 流程实例id
	String runtimeid;
	// 参与者
	String participant;
	// 操作模式 办理、抄送还是抄阅 中文提示
	String operatemodetip;
	// 操作模式 办理、抄送还是抄阅
	String operatemode;

	TWfActive act;

	List<TWfRelevant> rev;

	String extinfo;

	public String getExtinfo() {
		return extinfo;
	}

	public void setExtinfo(String extinfo) {
		this.extinfo = extinfo;
	}

	public List<TWfRelevant> getRev() {
		return rev;
	}

	public void setRev(List<TWfRelevant> rev) {
		this.rev = rev;
	}

	public String getBussid() {
		return bussid;
	}

	public void setBussid(String bussid) {
		this.bussid = bussid;
	}

	public String getBussurl() {
		return bussurl;
	}

	public void setBussurl(String bussurl) {
		this.bussurl = bussurl;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getWorkcode() {
		return workcode;
	}

	public void setWorkcode(String workcode) {
		this.workcode = workcode;
	}

	public String getBusstip() {
		return busstip;
	}

	public void setBusstip(String busstip) {
		this.busstip = busstip;
	}

	public String getRuntimeid() {
		return runtimeid;
	}

	public void setRuntimeid(String runtimeid) {
		this.runtimeid = runtimeid;
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public TWfActive getAct() {
		return act;
	}

	public void setAct(TWfActive act) {
		this.act = act;
	}

	public String getOperatemodetip() {
		return operatemodetip;
	}

	public void setOperatemodetip(String operatemodetip) {
		this.operatemodetip = operatemodetip;
	}

	public String getOperatemode() {
		return operatemode;
	}

	public void setOperatemode(String operatemode) {
		this.operatemode = operatemode;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

}
