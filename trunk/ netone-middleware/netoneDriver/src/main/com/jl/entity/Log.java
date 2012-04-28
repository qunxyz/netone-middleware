/**
 * 
 */
package com.jl.entity;

import java.util.Date;

/**
 * ������־ʵ����
 * 
 * @author Don (cai.you.dun)
 * @version 1.0.0 date Mar 25, 2010 create by Don
 * @history
 */
public class Log {

	/** UUID */
	private String logId;
	/** �û�ID */
	private String userId;
	/** �û����� */
	private String userName;
	/** ����ʱ�� */
	private Date operateTime;
	/** ����ID ����|�޸�|ɾ��|���|�ύ���(...) */
	private String operateId;
	/** �����Ϣ SUCCESS:FAILED */
	private String resultInfo;
	/** ��־���� */
	private String logSeq;
	/** �û�ʹ��IP */
	private String userIp;
	/** �û��ͻ�����Ϣ (�����������ϵͳ) */
	private String userAgent;
	/** HOST ����ʱ��鿴 */
	private String userHost;
	/** ��־��ע */
	private String remark;

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateId() {
		return operateId;
	}

	public void setOperateId(String operateId) {
		this.operateId = operateId;
	}

	public String getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}

	public String getLogSeq() {
		return logSeq;
	}

	public void setLogSeq(String logSeq) {
		this.logSeq = logSeq;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserHost() {
		return userHost;
	}

	public void setUserHost(String userHost) {
		this.userHost = userHost;
	}

}
