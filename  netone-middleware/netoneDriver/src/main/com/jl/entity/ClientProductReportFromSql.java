package com.jl.entity;

import java.math.BigDecimal;

public class ClientProductReportFromSql extends AbstractEntity {
	private String yxManagerCompany; // Ӫ������˾
	private String sellDepartment; // ���۲���
	private String operationDirector; // ҵ������
	private String clientLoginName; // �ͻ����
	private String clientName; // �ͻ�����
	private String clientid; // �ͻ�ID
	private String productCode; // ��Ʒ���
	private String productName; // ��Ʒ����
	private String specifications; // ��Ʒ���
	private Double sumWage; // �ܶ���
	private Double consignmentSum; // ������consignmentSum
	private String consignmentCount; // ������consignmentSum
	private Double invoicePrice; // ��Ʊ����
	private Double invoicePriceSum; // 
	private Double awardPriceSum; // ��Ʊ�ܼ�
	private Double awardPrice; // �����Ҹ�����
	private int indentId; // ����ID
	private Double bottle;
	private Integer productId;
	private BigDecimal bottleCount = new BigDecimal(0);// ƿ��
	public String getYxManagerCompany() {
		return yxManagerCompany;
	}
	public void setYxManagerCompany(String yxManagerCompany) {
		this.yxManagerCompany = yxManagerCompany;
	}
	public String getSellDepartment() {
		return sellDepartment;
	}
	public void setSellDepartment(String sellDepartment) {
		this.sellDepartment = sellDepartment;
	}
	public String getOperationDirector() {
		return operationDirector;
	}
	public void setOperationDirector(String operationDirector) {
		this.operationDirector = operationDirector;
	}
	public String getClientLoginName() {
		return clientLoginName;
	}
	public void setClientLoginName(String clientLoginName) {
		this.clientLoginName = clientLoginName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSpecifications() {
		return specifications;
	}
	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	public Double getSumWage() {
		return sumWage;
	}
	public void setSumWage(Double sumWage) {
		this.sumWage = sumWage;
	}
	public Double getConsignmentSum() {
		return consignmentSum;
	}
	public void setConsignmentSum(Double consignmentSum) {
		this.consignmentSum = consignmentSum;
	}
	public Double getInvoicePrice() {
		return invoicePrice;
	}
	public String getConsignmentCount() {
		return consignmentCount;
	}
	public void setConsignmentCount(String consignmentCount) {
		this.consignmentCount = consignmentCount;
	}
	public void setInvoicePrice(Double invoicePrice) {
		this.invoicePrice = invoicePrice;
	}
	public Double getInvoicePriceSum() {
		return invoicePriceSum;
	}
	public void setInvoicePriceSum(Double invoicePriceSum) {
		this.invoicePriceSum = invoicePriceSum;
	}
	public Double getAwardPriceSum() {
		return awardPriceSum;
	}
	public void setAwardPriceSum(Double awardPriceSum) {
		this.awardPriceSum = awardPriceSum;
	}
	public Double getAwardPrice() {
		return awardPrice;
	}
	public void setAwardPrice(Double awardPrice) {
		this.awardPrice = awardPrice;
	}
	public int getIndentId() {
		return indentId;
	}
	public void setIndentId(int indentId) {
		this.indentId = indentId;
	}
	public Double getBottle() {
		return bottle;
	}
	public void setBottle(Double bottle) {
		this.bottle = bottle;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public BigDecimal getBottleCount() {
		return bottleCount;
	}
	public void setBottleCount(BigDecimal bottleCount) {
		this.bottleCount = bottleCount;
	}


}
