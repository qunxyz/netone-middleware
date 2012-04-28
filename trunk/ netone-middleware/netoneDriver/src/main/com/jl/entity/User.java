/**
 * 
 */
package com.jl.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * �û���Ϣ��
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2010-11-24 ����02:38:59
 * @history
 */
public class User implements Serializable{

	/** �û�ID */
	private String userId;
	/** �û����� */
	private String userCode;
	/** �û��� */
	private String userName;
	/** ����/��˾���� */
	private String departmentId;
	/** ״̬ 1������ 0������ */
	private String status;
	/** ��չ��Ϣ */
	private String extendinfo;
	/** ���� */
	private String password;
	/** ���� x:ҵ������ 4:������ */
	private String types;
	/** ���� */
	private String description;
	/** ���� */
	private String email;
	/** ��ַ��Ϣ */
	private String addressinfo;
	/** ���� */
	private String fox;
	/** �绰 */
	private String phone;
	/** ��˾ */
	private String bussiness;
	/** ������ */
	private String participant;
	/** �Ա� */
	private String sex;
	/** �Ƿ�� */
	private String marriage;
	/** ΩһKEY ���֤.. */
	private String ids;
	/** רҵ */
	private String major;
	/** ����ʱ�� */
	private Date createdDate;
	/** �޸�ʱ�� */
	private Date modifyDate;
	/** ȡ��ʱ�� */
	private Date cancelDate;

	private String level;
	
	private String zw;
	private int accounttypes;
	private Date leavetime;
	private Date backtime;
	private String dlr;//������
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

	// ��չ��Ϣ
	/** ����/��˾���� */
	private String departmentCode;
	private String departmentName;
	private String parentDepartmentId;

	/** �ϼ�������Ϣ���� */
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
