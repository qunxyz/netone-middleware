package oe.common.workflow;

import java.io.Serializable;

/**
 * ��������
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:oesee@139.com<br>
 * 
 */
public class TWfWorklistExt implements Serializable {
	// ҵ��id
	String bussid;
	// ҵ���ַ��ͨ��revBussUrl+revBussId ���һ��������ҵ����ķ��ʵ�ַ
	String bussurl;
	// ���������һ���ύ��
	String customer;
	// ҵ������
	String busstype;
	// �����������ʱ��
	String starttime;
	// ���������ϸ����
	String activeName;
	String processName;
	// ��������
	String processid;
	String activeid;
	// �Code
	String workcode;
	// ҵ����ʾ
	String busstip;
	// ҵ��״̬
	String bussstatus;
	// ����ʵ��id
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
