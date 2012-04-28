package com.jl.entity;

public class ClientProductCategoriesInfo {
	private String clientid; // �ͻ�ID
	private String productCode; // ��Ʒ���
	private String productName; // ��Ʒ����
	private String specifications; // ��Ʒ���
	private Double sumWage; // �ܶ���
	private Double consignmentSum; // ������
	private Double invoicePrice; // ��Ʊ����
	private Double invoicePriceSum; // ��Ʊ�ܼ�
	private Double awardPrice; // �����Ҹ�����
	private Double awardPriceSum; // �����Ҹ��ܼ�
	private int indentId; // ����ID

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

	public void setInvoicePrice(Double invoicePrice) {
		this.invoicePrice = invoicePrice;
	}

	public Double getInvoicePriceSum() {
		return invoicePriceSum;
	}

	public void setInvoicePriceSum(Double invoicePriceSum) {
		this.invoicePriceSum = invoicePriceSum;
	}

	public Double getAwardPrice() {
		return awardPrice;
	}

	public void setAwardPrice(Double awardPrice) {
		this.awardPrice = awardPrice;
	}

	public Double getAwardPriceSum() {
		return awardPriceSum;
	}

	public void setAwardPriceSum(Double awardPriceSum) {
		this.awardPriceSum = awardPriceSum;
	}

	public int getIndentId() {
		return indentId;
	}

	public void setIndentId(int indentId) {
		this.indentId = indentId;
	}

}
