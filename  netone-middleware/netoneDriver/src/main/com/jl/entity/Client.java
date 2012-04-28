package com.jl.entity;

import java.io.Serializable;
import java.util.Date;

public class Client implements Serializable {

	/** �ͻ���� */
	private String clientId;
	/** �ͻ���½�� */
	private String clientCode;
	/** �ͻ����� */
	private String clientName;
	/** ҵ������ */
	private String operationDirector;
	/** ��ϵ�� */
	private String linkman;
	/** �ͻ��绰 */
	private String telphone;
	/** �ֻ�* */
	private String mobile;
	/** �ʼ���ַ* */
	private String email;
	/** ��˾��ַ */
	private String companyAddress;
	/** �ʹ�վ */
	private String servicestation;
	/** �ͻ���ַ */
	private String deliverGoodsAddress;
	/** ����ʺ� */
	private String remitAccounts;
	/** ������ */
	private String openAccountTitle;
	/** ��˰���� */
	private String ratepayingType;
	/** ��Լ��֤���� */
	private Double lyBailMonery;
	/** ����� */
	private String remitPersion;
	/** ���ʱ�� */
	private Date remitTime;

	/** *�ͻ�����*** */
	private String clientType;
	/** ****�г�����******* */
	private String marketType;

	/** ������ˮ�� ��Ӧ�����̼��𻮷ֱ� */
	private String priceLevelCode;

	/** �����̱�ʶ 2010-5-11 */
	private String clientTag;

	/** ���� ���Ʋ�ͬ�������̵Ķ�����ͷ�������������þƲ����ƣ� */
	private Double tons;

	/** ȡ��ʱ�� */
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
