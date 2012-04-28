package com.jl.entity;

import java.io.Serializable;
import java.util.Date;

public class Client implements Serializable {

	/** 客户编号 */
	private String clientId;
	/** 客户登陆名 */
	private String clientCode;
	/** 客户名称 */
	private String clientName;
	/** 业务主任 */
	private String operationDirector;
	/** 联系人 */
	private String linkman;
	/** 客户电话 */
	private String telphone;
	/** 手机* */
	private String mobile;
	/** 邮件地址* */
	private String email;
	/** 公司地址 */
	private String companyAddress;
	/** 送达站 */
	private String servicestation;
	/** 送货地址 */
	private String deliverGoodsAddress;
	/** 汇款帐号 */
	private String remitAccounts;
	/** 开户行 */
	private String openAccountTitle;
	/** 纳税类型 */
	private String ratepayingType;
	/** 履约保证金金额 */
	private Double lyBailMonery;
	/** 汇款人 */
	private String remitPersion;
	/** 汇款时间 */
	private Date remitTime;

	/** *客户类型*** */
	private String clientType;
	/** ****市场类型******* */
	private String marketType;

	/** 级别流水号 对应经销商级别划分表 */
	private String priceLevelCode;

	/** 经销商标识 2010-5-11 */
	private String clientTag;

	/** 吨数 限制不同区域经销商的订单最低发货吨数（广告用酒不限制） */
	private Double tons;

	/** 取消时间 */
	private Date cancelTime;

	private Double advanceTagPayment;

	public Double getAdvanceTagPayment() {
		return advanceTagPayment;
	}

	public void setAdvanceTagPayment(Double advanceTagPayment) {
		this.advanceTagPayment = advanceTagPayment;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getOperationDirector() {
		return operationDirector;
	}

	public void setOperationDirector(String operationDirector) {
		this.operationDirector = operationDirector;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getServicestation() {
		return servicestation;
	}

	public void setServicestation(String servicestation) {
		this.servicestation = servicestation;
	}

	public String getDeliverGoodsAddress() {
		return deliverGoodsAddress;
	}

	public void setDeliverGoodsAddress(String deliverGoodsAddress) {
		this.deliverGoodsAddress = deliverGoodsAddress;
	}

	public String getRemitAccounts() {
		return remitAccounts;
	}

	public void setRemitAccounts(String remitAccounts) {
		this.remitAccounts = remitAccounts;
	}

	public String getOpenAccountTitle() {
		return openAccountTitle;
	}

	public void setOpenAccountTitle(String openAccountTitle) {
		this.openAccountTitle = openAccountTitle;
	}

	public String getRatepayingType() {
		return ratepayingType;
	}

	public void setRatepayingType(String ratepayingType) {
		this.ratepayingType = ratepayingType;
	}

	public String getRemitPersion() {
		return remitPersion;
	}

	public void setRemitPersion(String remitPersion) {
		this.remitPersion = remitPersion;
	}

	public Date getRemitTime() {
		return remitTime;
	}

	public void setRemitTime(Date remitTime) {
		this.remitTime = remitTime;
	}

	public Double getLyBailMonery() {
		return lyBailMonery;
	}

	public void setLyBailMonery(Double lyBailMonery) {
		this.lyBailMonery = lyBailMonery;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getMarketType() {
		return marketType;
	}

	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

	public String getPriceLevelCode() {
		return priceLevelCode;
	}

	public void setPriceLevelCode(String priceLevelCode) {
		this.priceLevelCode = priceLevelCode;
	}

	public String getClientTag() {
		return clientTag;
	}

	public void setClientTag(String clientTag) {
		this.clientTag = clientTag;
	}

	public Double getTons() {
		return tons;
	}

	public void setTons(Double tons) {
		this.tons = tons;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

}
