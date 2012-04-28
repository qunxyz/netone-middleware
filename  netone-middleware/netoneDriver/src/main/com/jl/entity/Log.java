/**
 * 
 */
package com.jl.entity;

import java.util.Date;

/**
 * 操作日志实体类
 * 
 * @author Don (cai.you.dun)
 * @version 1.0.0 date Mar 25, 2010 create by Don
 * @history
 */
public class Log {

	/** UUID */
	private String logId;
	/** 用户ID */
	private String userId;
	/** 用户名称 */
	private String userName;
	/** 操作时间 */
	private Date operateTime;
	/** 操作ID 新增|修改|删除|审核|提交审核(...) */
	private String operateId;
	/** 结果信息 SUCCESS:FAILED */
	private String resultInfo;
	/** 日志序列 */
	private String logSeq;
	/** 用户使用IP */
	private String userIp;
	/** 用户客户端信息 (浏览器、操作系统) */
	private String userAgent;
	/** HOST 负载时候查看 */
	private String userHost;
	/** 日志备注 */
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
