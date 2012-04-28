/**
 * 
 */
package com.jl.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2010-11-24 下午02:38:59
 * @history
 */
public class User implements Serializable{

	/** 用户ID */
	private String userId;
	/** 用户代码 */
	private String userCode;
	/** 用户名 */
	private String userName;
	/** 部门/公司隶属 */
	private String departmentId;
	/** 状态 1：启用 0：禁用 */
	private String status;
	/** 扩展信息 */
	private String extendinfo;
	/** 密码 */
	private String password;
	/** 类型 x:业务主任 4:经销商 */
	private String types;
	/** 描述 */
	private String description;
	/** 邮箱 */
	private String email;
	/** 地址信息 */
	private String addressinfo;
	/** 传真 */
	private String fox;
	/** 电话 */
	private String phone;
	/** 公司 */
	private String bussiness;
	/** 参与者 */
	private String participant;
	/** 性别 */
	private String sex;
	/** 是否婚 */
	private String marriage;
	/** 惟一KEY 身份证.. */
	private String ids;
	/** 专业 */
	private String major;
	/** 创建时间 */
	private Date createdDate;
	/** 修改时间 */
	private Date modifyDate;
	/** 取消时间 */
	private Date cancelDate;

	private String level;
	
	private String zw;
	private int accounttypes;
	private Date leavetime;
	private Date backtime;
	private String dlr;//代理人
	private int notice;
	private int orders;
	
	//ext
	private String nLevelName;
	

	public String getNLevelName() {
		return nLevelName;
	}

	public void setNLevelName(String levelName) {
		nLevelName = levelName;
	}

	public String getZw() {
		return zw;
	}

	public void setZw(String zw) {
		this.zw = zw;
	}

	public int getAccounttypes() {
		return accounttypes;
	}

	public void setAccounttypes(int accounttypes) {
		this.accounttypes = accounttypes;
	}

	public Date getLeavetime() {
		return leavetime;
	}

	public void setLeavetime(Date leavetime) {
		this.leavetime = leavetime;
	}

	public Date getBacktime() {
		return backtime;
	}

	public void setBacktime(Date backtime) {
		this.backtime = backtime;
	}

	public String getDlr() {
		return dlr;
	}

	public void setDlr(String dlr) {
		this.dlr = dlr;
	}

	public int getNotice() {
		return notice;
	}

	public void setNotice(int notice) {
		this.notice = notice;
	}

	// 扩展信息
	/** 部门/公司隶属 */
	private String departmentCode;
	private String departmentName;
	private String parentDepartmentId;

	/** 上级数据信息集合 */
	private Department departmentLevelRow;
	private Department parentDepartment;

	public Department getParentDepartment() {
		return parentDepartment;
	}

	public void setParentDepartment(Department parentDepartment) {
		this.parentDepartment = parentDepartment;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExtendinfo() {
		return extendinfo;
	}

	public void setExtendinfo(String extendinfo) {
		this.extendinfo = extendinfo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddressinfo() {
		return addressinfo;
	}

	public void setAddressinfo(String addressinfo) {
		this.addressinfo = addressinfo;
	}

	public String getFox() {
		return fox;
	}

	public void setFox(String fox) {
		this.fox = fox;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBussiness() {
		return bussiness;
	}

	public void setBussiness(String bussiness) {
		this.bussiness = bussiness;
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getParentDepartmentId() {
		return parentDepartmentId;
	}

	public void setParentDepartmentId(String parentDepartmentId) {
		this.parentDepartmentId = parentDepartmentId;
	}

	public Department getDepartmentLevelRow() {
		return departmentLevelRow;
	}

	public void setDepartmentLevelRow(Department departmentLevelRow) {
		this.departmentLevelRow = departmentLevelRow;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}

}
