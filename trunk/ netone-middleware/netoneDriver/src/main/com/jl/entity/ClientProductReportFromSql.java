package com.jl.entity;

import java.math.BigDecimal;

public class ClientProductReportFromSql extends AbstractEntity {
	private String yxManagerCompany; // 营销管理公司
	private String sellDepartment; // 销售部门
	private String operationDirector; // 业务主任
	private String clientLoginName; // 客户编号
	private String clientName; // 客户名称
	private String clientid; // 客户ID
	private String productCode; // 产品编号
	private String productName; // 产品名称
	private String specifications; // 产品规格
	private Double sumWage; // 总吨数
	private Double consignmentSum; // 总箱数consignmentSum
	private String consignmentCount; // 总箱数consignmentSum
	private Double invoicePrice; // 发票单价
	private Double invoicePriceSum; // 
	private Double awardPriceSum; // 发票总价
	private Double awardPrice; // 奖励兑付单价
	private int indentId; // 订单ID
	private Double bottle;
	private Integer productId;
	private BigDecimal bottleCount = new BigDecimal(0);// 瓶数
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
