/**
 * 
 */
package com.jl.entity;

import java.util.Date;


public class Shopping extends AbstractEntity {
	
	private String shoppingId;
	private String purchaseIllustrate;// 请购说明
	private String clientId; //请购人
	private String price; //金额
	private int status;
	
	//ext
	private String cid;
	private String cname;
	
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getShoppingId() {
		return shoppingId;
	}
	public void setShoppingId(String shoppingId) {
		this.shoppingId = shoppingId;
	}
	public String getPurchaseIllustrate() {
		return purchaseIllustrate;
	}
	public void setPurchaseIllustrate(String purchaseIllustrate) {
		this.purchaseIllustrate = purchaseIllustrate;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}


	
	
}
