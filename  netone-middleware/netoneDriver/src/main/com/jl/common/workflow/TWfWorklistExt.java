package com.jl.common.workflow;

import java.io.Serializable;
import java.util.List;

/**
 * ��������
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:oesee@139.com<br>
 * 
 */
public final class TWfWorklistExt implements Serializable {
	String appid;
	// ҵ��id
	String bussid;
	// ҵ���ַ��ͨ��revBussUrl+revBussId ���һ��������ҵ����ķ��ʵ�ַ
	String bussurl;
	// �����������ʱ��
	String starttime;
	// �Code
	String workcode;
	// ҵ����ʾ
	String busstip;
	// ����ʵ��id
	String runtimeid;
	// ������
	String participant;
	// ����ģʽ �������ͻ��ǳ��� ������ʾ
	String operatemodetip;
	// ����ģʽ �������ͻ��ǳ���
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
