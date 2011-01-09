package oe.common.workflow;

import java.io.Serializable;

/**
 * 代办任务
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:oesee@139.com<br>
 * 
 */
public class TWfWorklistExt implements Serializable {
	// 业务id
	String bussid;
	// 业务地址，通过revBussUrl+revBussId 组成一个完整的业务表单的访问地址
	String bussurl;
	// 该任务的上一个提交者
	String customer;
	// 业务特性
	String busstype;
	// 该任务的启动时间
	String starttime;
	// 该任务的详细名字
	String activeName;
	String processName;
	// 活动的设计名
	String processid;
	String activeid;
	// 活动Code
	String workcode;
	// 业务提示
	String busstip;
	// 业务状态
	String bussstatus;
	// 流程实例id
	String runtimeid;

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

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getBusstype() {
		return busstype;
	}

	public void setBusstype(String busstype) {
		this.busstype = busstype;
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

	public String getBussstatus() {
		return bussstatus;
	}

	public void setBussstatus(String bussstatus) {
		this.bussstatus = bussstatus;
	}

	public String getActiveName() {
		return activeName;
	}

	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getRuntimeid() {
		return runtimeid;
	}

	public void setRuntimeid(String runtimeid) {
		this.runtimeid = runtimeid;
	}

	public String getProcessid() {
		return processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getActiveid() {
		return activeid;
	}

	public void setActiveid(String activeid) {
		this.activeid = activeid;
	}

}
