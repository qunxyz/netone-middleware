package com.jl.entity;

import java.util.List;

public class ClientProductAnalysis {
	private String yxManagerCompany;				//营销管理公司
	private String sellDepartment;					//销售部门
	private String operationDirector;				//业务主任
	private String clientLoginName;					//客户编号
	private String clientName;						//客户名称
	private List<ClientProductCategoriesInfo> clientProductCategoriesInfoList;	//产品分类明细列表
	
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
	public List getClientProductCategoriesInfoList() {
		return clientProductCategoriesInfoList;
	}
	public void setClientProductCategoriesInfoList(
			List clientProductCategoriesInfoList) {
		this.clientProductCategoriesInfoList = clientProductCategoriesInfoList;
	}
	
}
