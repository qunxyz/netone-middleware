/**
 * 
 */
package com.jl.entity;

import java.util.Date;


public class ShoppingDetail extends AbstractEntity {
	
	private String shoppingDetailId;
	private String shoppingId;
	private String deviceName;// 设备名称
	private String units; //单位
	private String consignmentCount;//数量
	private String unitPrice;//单价
	private Date hopeDeliveryTime;//希望到货时间
	private String suggestBrand;// 建议品牌或厂家
	public String getShoppingDetailId() {
		return shoppingDetailId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public void setShoppingDetailId(String shoppingDetailId) {
		this.shoppingDetailId = shoppingDetailId;
	}
	public String getShoppingId() {
		return shoppingId;
	}
	public void setShoppingId(String shoppingId) {
		this.shoppingId = shoppingId;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public String getConsignmentCount() {
		return consignmentCount;
	}
	public void setConsignmentCount(String consignmentCount) {
		this.consignmentCount = consignmentCount;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Date getHopeDeliveryTime() {
		return hopeDeliveryTime;
	}
	public void setHopeDeliveryTime(Date hopeDeliveryTime) {
		this.hopeDeliveryTime = hopeDeliveryTime;
	}
	public String getSuggestBrand() {
		return suggestBrand;
	}
	public void setSuggestBrand(String suggestBrand) {
		this.suggestBrand = suggestBrand;
	}
	
	
}
